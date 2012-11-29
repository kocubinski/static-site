#!/bin/bash

cp -f bootswatch/cosmo/*.less bootstrap/less
lessc bootstrap/less/bootstrap.less > resources/public/bootswatch.css
