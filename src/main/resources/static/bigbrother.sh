#!/bin/bash

SERVER_IP="localhost"
SERVER_PORT=8080
DIRECTORY="."
AUTO_MODE=false
SENT_FILES_LOG="$DIRECTORY/.sent_files"

# returns xml files in the target directory
function get_xml_files() {
    for file in "$DIRECTORY"/*.xml; do
		[ -e "$file" ] || continue # No XML files exit

		if ! grep -Fxq "$(basename "$file")" "$SENT_FILES_LOG"; then
			echo "$(basename "$file")"
		fi
    done
}

# send xml files to the server
function send_xml_files() {
    for file in $(get_xml_files); do
        echo "Sending $file to server..."

		# Send file
        if curl -s -X POST http://$SERVER_IP:$SERVER_PORT/api/scans \
			-H "Content-Type: application/xml" \
			--data-binary @"$DIRECTORY/$file"; then

			# Log successful send
			echo "$file" >> "$SENT_FILES_LOG"
		else
			echo "Failed to send $file to $SERVER_IP at port: $SERVER_PORT" >&2
		fi
    done
}

function auto_mode() {
	# get the number of files in the target directory
	file_count=$(ls -l $DIRECTORY| grep ^- | wc -l)
	# watch for changes in the target directory
	while true; do
		if [ $file_count -ne $(ls -l $DIRECTORY | grep ^- | wc -l) ]; then
			send_xml_files
			file_count=$(ls -l $DIRECTORY | grep ^- | wc -l)
		fi
	done
}

# set up
echo "Setting up Big Brother..."

# No flag setup
if (( $# == 0 )); then
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
	# Automatic send mode
	read -p "Listen and send files to server automatically? (y/N):" response
	if [[ $response == "y" || $response == "Y" || $response == "yes" || $response == "Yes" ]]; then
		echo "Auto mode turned ON"
		AUTO_MODE=true
	else
		AUTO_MODE=false
	fi
fi

# Flag handling
while getopts "hai:p:d:" flag; do
	case $flag in
		h)
			echo "help flag selected"
			;;
		i)
			SERVER_IP=$OPTARG
			;;
		p)
			SERVER_PORT=$OPTARG
			;;
		d)
			DIRECTORY=$OPTARG
			;;
		a)
			AUTO_MODE=true
			echo "Auto mode turned ON"
			;;
		\?)
			;;
	esac
done

[ ! -f "$SENT_FILES_LOG" ] && touch "$SENT_FILES_LOG"

# Automatic mode
if [ "$AUTO_MODE" = true ]; then
	echo ""
	#inital send
	send_xml_files
	echo ""
	echo "Big Brother is watching..."
	auto_mode
else
	echo ""
	echo "Server:"
	echo "ip: $SERVER_IP"
	echo "port: $SERVER_PORT"
	echo "target directory: $DIRECTORY"
	echo ""
	send_xml_files
fi

echo ""
