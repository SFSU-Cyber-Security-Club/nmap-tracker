#!/bin/bash

STANDBY_MSG="\nBig Brother is watching...\n"

SERVER_IP="localhost"
SERVER_PORT=8080
DIRECTORY="."

# returns xml files in the target directory
get_xml_files() {
    for file in $(ls "$DIRECTORY"); do
        if [[ ${file: -4} == ".xml" ]]; then
            echo $file
        fi
    done
}

# send xml files to the server
send_xml_files() {
    for file in $(get_xml_files); do
        echo "Sending $file to server..."
        curl -s -X POST http://$SERVER_IP:$SERVER_PORT/api/scans -H "Content-Type: application/xml" --data-binary @$DIRECTORY/$file
    done
}

# set up
echo -n "Enter server IP: "
read SERVER_IP
echo -n "Enter server port: "
read SERVER_PORT
echo -n "Enter directory to watch: "
read DIRECTORY

# initial send
send_xml_files
echo "$STANDBY_MSG"

# get the number of files in the target directory
file_count=$(ls -l $DIRECTORY| grep ^- | wc -l)
# watch for changes in the target directory
while true; do
    if [ $file_count -ne $(ls -l $DIRECTORY | grep ^- | wc -l) ]; then
        send_xml_files
        file_count=$(ls -l $DIRECTORY | grep ^- | wc -l)
        echo "$STANDBY_MSG"
    fi
done
