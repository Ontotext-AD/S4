@Grab(group='org.codehaus.groovy.modules.http-builder',
	module='http-builder', version='0.5.2' )
import groovyx.net.http.*
import org.apache.http.entity.FileEntity
import org.apache.http.util.EntityUtils

def onlineServiceEndpoint = 'https://s4.ontotext.com/online-processing/item/'
def shopItemId = '2'

def keyId = '<your-credentials-here>'
def password = '<your-credentials-here>'

def client = new RESTClient(onlineServiceEndpoint + shopItemId)
client.auth.basic(keyId, password)

def builder = new groovy.json.JsonBuilder();
builder(
	document : "" + //embed the document to process in the request body
				"{    " +
				"\"created_at\": \"Tue Jan 21 10:09:50 +0000 2014\"," +
				"    \"id\": 425570928198561800," +
				"    \"id_str\": \"425570928198561792\"," +
				"    \"text\": \"halo @shafiqah5387 kami menjual Cord Holder edisi rilakkuma, korilakkuma dan kiiroitori. Harga Rp. 30.000/2pcs aja :) http://t.co/oOJRmipac1\"," +
				"    \"source\": \"web\"," +
				"    \"truncated\": false," +
				"    \"in_reply_to_status_id\": 425565523825266700," +
				"    \"in_reply_to_status_id_str\": \"425565523825266688\"," +
				"    \"in_reply_to_user_id\": 425061303," +
				"    \"in_reply_to_user_id_str\": \"425061303\"," +
				"    \"in_reply_to_screen_name\": \"shafiqah5387\"," +
				"    \"user\": {" +
				"        \"id\": 2275508352," +
				"        \"id_str\": \"2275508352\"," +
				"        \"name\": \"Eileen by adinda\"," +
				"        \"screen_name\": \"eileenbyadinda\"," +
				"        \"location\": \"Jakarta\"," +
				"        \"url\": \"http://eileenbyadinda.tumblr.com\"," +
				"        \"description\": \"Jual berbagai tas, hijab &gadget equipment import HK & Korea. Whatsapp / sms: Adinda - 085692273224. Thank you!\"," +
				"        \"protected\": false," +
				"        \"followers_count\": 24," +
				"        \"friends_count\": 60," +
				"        \"listed_count\": 0," +
				"        \"created_at\": \"Sat Jan 04 03:48:02 +0000 2014\"," +
				"        \"favourites_count\": 0," +
				"        \"utc_offset\": 25200," +
				"        \"time_zone\": \"Bangkok\"," +
				"        \"geo_enabled\": false," +
				"        \"verified\": false," +
				"        \"statuses_count\": 24," +
				"        \"lang\": \"en\"," +
				"        \"contributors_enabled\": false," +
				"        \"is_translator\": false," +
				"        \"profile_background_color\": \"C0DEED\"," +
				"        \"profile_background_image_url\": \"http://abs.twimg.com/images/themes/theme1/bg.png\"," +
				"        \"profile_background_image_url_https\": \"https://abs.twimg.com/images/themes/theme1/bg.png\"," +
				"        \"profile_background_tile\": false," +
				"        \"profile_image_url\": \"http://pbs.twimg.com/profile_images/419315801309450240/BV9KiP3P_normal.jpeg\"," +
				"        \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/419315801309450240/BV9KiP3P_normal.jpeg\"," +
				"        \"profile_link_color\": \"0084B4\"," +
				"        \"profile_sidebar_border_color\": \"C0DEED\"," +
				"        \"profile_sidebar_fill_color\": \"DDEEF6\"," +
				"        \"profile_text_color\": \"333333\"," +
				"        \"profile_use_background_image\": true," +
				"        \"default_profile\": true," +
				"        \"default_profile_image\": false," +
				"        \"following\": null," +
				"        \"follow_request_sent\": null," +
				"        \"notifications\": null" +
				"    }," +
				"    \"geo\": null," +
				"    \"coordinates\": null," +
				"    \"place\": null," +
				"    \"contributors\": null," +
				"    \"retweet_count\": 0," +
				"    \"favorite_count\": 0," +
				"    \"entities\": {" +
				"        \"hashtags\": []," +
				"        \"symbols\": []," +
				"        \"urls\": []," +
				"        \"user_mentions\": [" +
				"            {" +
				"                \"screen_name\": \"shafiqah5387\"," +
				"                \"name\": \"¦Shafiqah Ismail¦ ?\"," +
				"                \"id\": 425061303," +
				"                \"id_str\": \"425061303\"," +
				"                \"indices\": [" +
				"                    5," +
				"                    18" +
				"                ]" +
				"            }" +
				"        ]," +
				"        \"media\": [" +
				"            {" +
				"                \"id\": 425570928206950400," +
				"                \"id_str\": \"425570928206950400\"," +
				"                \"indices\": [" +
				"                    118," +
				"                    140" +
				"                ], " +
				"               \"media_url\": \"http://pbs.twimg.com/media/BefugSKCMAAGunF.jpg\"," +
				"                \"media_url_https\": \"https://pbs.twimg.com/media/BefugSKCMAAGunF.jpg\"," +
				"                \"url\": \"http://t.co/oOJRmipac1\"," +
				"                \"display_url\": \"pic.twitter.com/oOJRmipac1\"," +
				"                \"expanded_url\": \"http://twitter.com/eileenbyadinda/status/425570928198561792/photo/1\"," +
				"                \"type\": \"photo\"," +
				"                \"sizes\": {" +
				"                    \"small\": {" +
				"                        \"w\": 340," +
				"                        \"h\": 331," +
				"                        \"resize\": \"fit\"" +
				"                    }," +
				"                    \"large\": {" +
				"                        \"w\": 585," +
				"                        \"h\": 570," +
				"                        \"resize\": \"fit\"" +
				"                    }," +
				"                    \"thumb\": {" +
				"                        \"w\": 150," +
				"                        \"h\": 150," +
				"                        \"resize\": \"crop\"" +
				"                    }," +
				"                    \"medium\": {" +
				"                        \"w\": 585," +
				"                        \"h\": 570," +
				"                        \"resize\": \"fit\"" +
				"                    }" +
				"                }" +
				"            }" +
				"        ]" +
				"    }," +
				"    \"favorited\": false," +
				"    \"retweeted\": false," +
				"    \"possibly_sensitive\": false," +
				"    \"filter_level\": \"medium\"," +
				"    \"lang\": \"id\"" +
				"}",
	mimeType : "text/x-json-twitter", //treat this document as twitter json
	annotationSelectors : [":", "Original markups:"] //output annotations from the default and Original markups sets
	);

println("Request body is:")
println(builder.toPrettyString())
def response = client.post(
	body : builder.toString(), //set request body
	requestContentType : "application/json",  //request body is always JSON, application/json is the only valid value for the Content-Type header
	contentType : "application/gate+json") //request GATE JSON output
println response.getStatusLine()
println response.data.text