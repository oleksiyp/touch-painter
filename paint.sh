#!/bin/bash
export DISPLAY=:2
X -nocursor $DISPLAY &
X_PID=$!
google-chrome --window-size=1600,900 --window-position=0,0 --kiosk index.onStart --user-data-dir=/var/tmp/3
kill $X_PID