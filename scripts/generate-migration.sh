#!/usr/bin/env bash

TIME=$(date +%s)
FILE_PATH=./src/main/resources/db/changelog/mysql/$TIME-$1.sql
echo generating "$FILE_PATH"

cat > "$FILE_PATH" <<EOF
-- liquibase formatted sql

-- changeset $TIME:$1
-- rollback
EOF
