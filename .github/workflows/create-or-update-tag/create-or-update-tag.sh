#!/bin/sh

# Get the tag name from the input
TAG_NAME=$INPUT_TAG

# Check if the tag already exists
EXISTS=$(git ls-remote --tags origin $TAG_NAME | wc -l)

if [ $EXISTS -eq 0 ]
then
  # Create the new tag if it doesn't exist
  git tag $TAG_NAME
  git push origin $TAG_NAME
else
  # Update the existing tag if it does exist
  git tag -f $TAG_NAME
  git push origin $TAG_NAME --force
fi