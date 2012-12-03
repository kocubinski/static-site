#!/usr/bin/env/python

import subprocess
import sys
import re

inputFile = sys.argv[1]
template = open("resources/templates/template.html").read()
content = subprocess.check_output(["markdown", inputFile])
print re.sub("%CONTENT%", content, template)
