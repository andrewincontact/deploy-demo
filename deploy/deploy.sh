#!/usr/bin/env bash
# Deploys one image tag, checks health, rolls back automatically on failure.
set -euo pipefail

TAG="${1:?usage: deploy.sh <image-tag>}"

if [[ ! "$TAG" =~ ^[0-9a-f]{7,40}$ ]]; then
  echo "Refusing to deploy: invalid tag '$TAG'" >&2
  exit 2
fi

cd /opt/deploy-demo
[ -f .env ] && cp .env .env.previous
echo "IMAGE_TAG=$TAG" > .env

docker compose pull
docker compose up -d

for i in $(seq 1 30); do
  if curl -fs http://localhost:8080/actuator/health > /dev/null; then
    echo "Deployed and healthy: $TAG"
    exit 0
  fi
  sleep 2
done

echo "Not healthy after 60 s — rolling back" >&2
docker compose logs --tail=50 >&2
if [ -f .env.previous ]; then
  cp .env.previous .env
  docker compose up -d
fi
exit 1