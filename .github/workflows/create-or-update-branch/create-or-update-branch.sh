#!/bin/sh

# Get the branch name from the input
BRANCH_NAME=$INPUT_BRANCH

# Check if the branch already exists
EXISTS=$(git ls-remote --heads origin $BRANCH_NAME | wc -l)

if [ $EXISTS -eq 0 ]
then
  # Create the new branch if it doesn't exist
  git checkout -b $BRANCH_NAME
  git push -u origin $BRANCH_NAME
else
  # Check out the existing branch if it does exist
  git checkout $BRANCH_NAME
  git pull origin $BRANCH_NAME
fi
