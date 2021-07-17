#!/usr/bin/env bash

set -e

./gradlew cleanTest test --tests GenerateSwagger
./gradlew openApiGenerate
cd build/api-sdk
npm install --force
npm run build
cd dist
npm publish
cd ../../..
rm swagger.json