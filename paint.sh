#!/bin/bash
export DISPLAY=:2
X -nocursor $DISPLAY &
X_PID=$!
./gradlew webpack-run
google-chrome --window-size=1600,900 --window-position=0,0 --kiosk http://localhost:9002 --user-data-dir=/var/tmp/3
./gradlew webpack-stop
kill $X_PID