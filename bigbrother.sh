#!/bin/bash

STANDBY_MSG="Big Brother is watching..."

SERVER_IP="localhost"
SERVER_PORT=8080
DIRECTORY="."

# returns xml files in the target directory
function get_xml_files() {
    for file in $(ls "$DIRECTORY"); do
        if [[ ${file: -4} == ".xml" ]]; then
            echo $file
        fi
    done
}

# send xml files to the server
function send_xml_files() {
    for file in $(get_xml_files); do
        echo "Sending $file to server..."

        curl -s -X POST http://$SERVER_IP:$SERVER_PORT/api/scans -H "Content-Type: application/xml" --data-binary @$DIRECTORY/$file
        echo ""
    done
}

# set up
echo "Setting up Big Brother..."
# server ip address
read -p "Enter the server IP address: " response
if [ -z "$response" ]; then
    echo "Using default server IP address: $SERVER_IP"
else
    SERVER_IP=$response
fi
# server port
read -p "Enter the server port : " response
if [ -z "$response" ]; then
    echo "Using default server port: $SERVER_PORT"
else
    SERVER_PORT=$response
fi
# target directory
read -p "Enter the target directory : " response
if [ -z "$response" ]; then
    echo "Using default server port: $DIRECTORY"
else
    DIRECTORY=$response
fi

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
