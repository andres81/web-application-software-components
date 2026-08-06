#!/usr/bin/env sh

#
# Copyright 2026 André Schepers
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

openssl ecparam -out rootCA.key -name prime256v1 -genkey;

openssl req -new -sha256 -key rootCA.key -out rootCA.csr;
openssl x509 -req -sha256 -days 3650 -in rootCA.csr -signkey rootCA.key -out \
rootCA.crt;

openssl genrsa -out server.key 2048;

openssl req -new -key server.key -out server.csr;

openssl x509 -req -in server.csr -CA rootCA.crt -CAkey rootCA.key \
-CAcreateserial -out server.crt -days 365 -sha256;



