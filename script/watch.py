#!/usr/bin/env/python

import time
import subprocess
import sys
import re
import os

from watchdog.observers import Observer
from watchdog.events import LoggingEventHandler, FileSystemEventHandler

def write_html(input_file):
    template = open("resources/templates/template.html").read()
    content = subprocess.check_output(["markdown", input_file])
    out_path = "html/" + os.path.splitext(os.path.basename(input_file))[0] + ".html"
    print out_path
    out_file = open(out_path, 'w')
    out_file.write(re.sub("%CONTENT%", content, template))
    out_file.close()

class ModifiedHandler(FileSystemEventHandler):

    def on_modified(self, event):
        print event.src_path
        write_html(event.src_path)
        

if __name__ == "__main__":
    event_handler = ModifiedHandler()
    observer = Observer()
    observer.schedule(event_handler, path='resources/site', recursive=True)
    observer.start()
    try:
        while True:
            time.sleep(0.3)
    except KeyboardInterrupt:
        observer.stop()
    observer.join()
