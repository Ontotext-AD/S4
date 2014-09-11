PROTOCOL="https://"
ENDPOINT_URL="text.s4.ontotext.com/v1/"
SERVICE_ID="twitie"

# API Key
KEY_ID="<your-credentials-here>"
PASSWORD="<your-credentials-here>"

# Headers
ACCEPT="application/json"
CONTENT_TYPE="application/json"

# POST body parameters
DOCUMENT="Tiruchirappalli is the  fourth largest city in the Indian state of  Tamil Nadu and is the administrative headquarters  of Tiruchirappalli District. Its recorded  history begins in the 3rd century BC,  when it was under the rule of the Cholas.  The city has also been ruled by the Pandyas,  Pallavas, Vijayanagar Empire, Nayak Dynasty,  the Carnatic state and the British.  It played a crucial role in the Carnatic Wars  (1746.63) between the British and the French  East India companies. During British rule, the city  was popular for the Trichinopoly cigar, its unique brand  of cheroot. Monuments include the Rockfort (pictured), the  Ranganathaswamy temple and the Jambukeswarar temple.  It is an important educational centre in Tamil Nadu,  housing nationally recognised institutions such as the  Indian Institute of Management and the National  Institute of Technology."
DOCUMENT_MIME_TYPE="text/plain"
JSON_BODY="{\"document\" : \"$DOCUMENT\", \"documentType\" : \"$DOCUMENT_MIME_TYPE\"}"

echo -e "Processing an embedded plain text document...\n"
echo -e "Request body is: "
echo -e $JSON_BODY
echo -e "\n"

PIPELINE_ENDPOINT="$PROTOCOL$KEY_ID:$PASSWORD@$ENDPOINT_URL$SERVICE_ID"
echo -e "Pipeline endpoint is:"
echo -e "$PIPELINE_ENDPOINT\n"

curl -X POST -w "\n\n\nContent-Type:%{content_type}\nHTTP Code: %{http_code}\n" -H "Content-Type: $CONTENT_TYPE" -H "Accept: $ACCEPT" -d "$JSON_BODY" $PIPELINE_ENDPOINT
