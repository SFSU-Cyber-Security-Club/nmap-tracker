#!/bin/bash

# get the number of files in the current directory
file_count=$(ls -l | grep ^- | wc -l)

while true; do
    if [ $file_count -ne $(ls -l | grep ^- | wc -l) ]; then
        # TODO: action when the number of files in the directory changes
        echo "File count changed!"
        file_count=$(ls -l | grep ^- | wc -l)
    fi
done
