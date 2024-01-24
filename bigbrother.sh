#!/bin/bash

SERVER_PORT=8080

# returns xml files in the current directory
get_xml_files() {
    $(find . -maxdepth 1 -type f -name '*.xml')
}

# send xml files to the server
send_xml_files() {
    for file in $(get_xml_files); do
        # curl -X POST -F "file=@$file" http://localhost:$SERVER_PORT/upload
        echo "Sending $file to server..."
    done
}

# initial send
send_xml_files

# get the number of files in the current directory
file_count=$(ls -l | grep ^- | wc -l)
# watch for changes in the current directory
while true; do
    if [ $file_count -ne $(ls -l | grep ^- | wc -l) ]; then
        send_xml_files
        file_count=$(ls -l | grep ^- | wc -l)
    fi
done
