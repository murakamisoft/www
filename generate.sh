#!/bin/bash

TEMPLATE="_templete.html"
OUTPUT_DIR="linux"
INPUT_FILE="articles.tsv"

mkdir -p "$OUTPUT_DIR"

# Windows改行対策
sed -i 's/\r$//' "$INPUT_FILE"

while IFS=$'\t' read -r CATEGORY TITLE DESCRIPTION FILE_NAME
do
  # trim（空白除去）
  CATEGORY=$(echo "$CATEGORY" | xargs)
  TITLE=$(echo "$TITLE" | xargs)
  DESCRIPTION=$(echo "$DESCRIPTION" | xargs)
  FILE_NAME=$(echo "$FILE_NAME" | xargs)

  # 空行スキップ
  if [ -z "$FILE_NAME" ]; then
    continue
  fi

  echo "Generating: $FILE_NAME"

  cp "$TEMPLATE" "$OUTPUT_DIR/$FILE_NAME"

  FILE_PATH="$OUTPUT_DIR/$FILE_NAME"

  # HTMLコメント形式で埋め込み（AI用メタ）
  sed -i "s|__FILE_NAME__|$FILE_NAME|g" "$FILE_PATH"
  sed -i "s|__CATEGORY__|$CATEGORY|g" "$FILE_PATH"
  sed -i "s|__TITLE__|$TITLE|g" "$FILE_PATH"
  sed -i "s|__DESCRIPTION__|$DESCRIPTION|g" "$FILE_PATH"

done < "$INPUT_FILE"

echo "DONE"