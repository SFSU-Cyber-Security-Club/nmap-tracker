#!/bin/bash

STANDBY_MSG="Big Brother is watching..."

SERVER_IP="localhost"
SERVER_PORT=8080

# returns xml files in the current directory
get_xml_files() {
    for file in $(ls); do
        if [[ ${file: -4} == ".xml" ]]; then
            echo $file
        fi
    done
}

# send xml files to the server
send_xml_files() {
    for file in $(get_xml_files); do
        echo "Sending $file to server..."
        curl -X POST -F "file=@$file" http://$SERVER_IP:$SERVER_PORT/upload
    done
}

# set up
echo -n "Enter server IP: "
read SERVER_IP
echo -n "Enter server port: "
read SERVER_PORT

# initial send
send_xml_files
echo "$STANDBY_MSG"

# get the number of files in the current directory
file_count=$(ls -l | grep ^- | wc -l)
# watch for changes in the current directory
while true; do
    if [ $file_count -ne $(ls -l | grep ^- | wc -l) ]; then
        send_xml_files
        file_count=$(ls -l | grep ^- | wc -l)
        echo "$STANDBY_MSG"
    fi
done
