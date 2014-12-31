# BURECHO Server #
This is the repository for the BURECHO server. You can find the last version available
here. BURECHO is a server-client service provided by INSURV. It acts  like an
echo server in a limited area (e.g. workplace, LAN, MAN) with surveillance purposes. While the basic implementation
of the server is very limited (basic echo server), this isn't a complete version yet.

## What's on the first release? ##
The first release (codenamed *MULTIECHO*) features a few changes from the initial draft:

* Multi-threaded server, therefore support of multiple connections at the same time.
* Assignation of a reference to every connection and saving of the ECHO'd data to the db/ directory.

## BURECHO and KATYA ##
KATYA (another project in development by INSURV) recollects information from a client and saves it using
**references** (e.g. *K2TINB147436321*). KATYA is still work in progress but the initial idea was:

* An event is triggered in a surveilled device and the regarding information is sent through BURECHO to one of
  the main servers (SURVID, BUSURV, GOVSURV).
* The BURECHO server replies by echoing the same information to the client.
* The BURECHO data is saved in one of the servers.

Later on (KATYA-specific, no BURECHO is involved at this point):

* The KATYA client hosted in the same BURECHO server requests an encrypted connection with a MOTHER server (where KATYA is hosted)
* The KATYA server accepts the connection.
* The KATYA client sends a reference to data to MOTHER.
* The KATYA server saves this reference for further use.

When some data wants to be retrieved:

* The user asks KATYA for a list of references.
* KATYA sends the user the list of references.
* The user sends a reference to KATYA.
* KATYA connects to the data server using a variation of the ECHO protocol, sending the reference the used is looking for.
* The data server looks for the referenced file. and sends its content to KATYA (pseudo-echo).
* KATYA sends the data to the user.