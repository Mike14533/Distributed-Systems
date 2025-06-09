package backend;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class ParseBountys {

    boolean isId = false;
    boolean isName = false;
    boolean isCrew = false;
    boolean isBounty = false;
    boolean isPosition = false;
    boolean isAge = false;
    boolean isGender = false;

    public List<Bounty> parseFromFile(String s) throws Exception {
        List<Bounty> bountys = new ArrayList<>();
        Bounty bounty = null;


        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(s));

        int eventType = parser.getEventType();

      
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();

            if (eventType == XmlPullParser.START_TAG) {
                
                if (tagName.equals("OnePiecePirates")) {
                    bounty = new Bounty();
                } else if (tagName.equals("id")) {
                    isId = true;
                } else if (tagName.equals("name")) {
                    isName = true;
                } else if (tagName.equals("crew")) {
                    isCrew = true;
                } else if (tagName.equals("bounty")) {
                    isBounty = true;
                } else if (tagName.equals("position")) {
                    isPosition = true;
                } else if (tagName.equals("age")) {
                    isAge = true;
                } else if (tagName.equals("gender")) {
                    isGender = true;
                }
            } else if (eventType == XmlPullParser.END_TAG) {
           
                if (tagName.equals("id")) {
                    isId = false;
                } else if (tagName.equals("name")) {
                    isName = false;
                } else if (tagName.equals("crew")) {
                    isCrew = false;
                } else if (tagName.equals("bounty")) {
                    isBounty = false;
                } else if (tagName.equals("position")) {
                    isPosition = false;
                } else if (tagName.equals("age")) {
                    isAge = false;
                } else if (tagName.equals("gender")) {
                    isGender = false;
                } else if (tagName.equals("OnePiecePirates")) {
                    if (bounty != null) {
                        bountys.add(bounty);  
                    }
                    bounty = null;  
                }
            }

  
            if (eventType == XmlPullParser.TEXT) {
                String text = parser.getText().trim();
                if (!text.isEmpty() && bounty != null) {
                    try {
   
                        if (isId) {
                            bounty.setId(Integer.parseInt(text));  
                        } else if (isName) {
                            bounty.setName(text);
                        } else if (isCrew) {
                            bounty.setCrew(text);
                        } else if (isBounty) {
                            bounty.setBounty(Integer.parseInt(text)); 
                        } else if (isPosition) {
                            bounty.setPosition(text);
                        } else if (isAge) {
                            bounty.setAge(Integer.parseInt(text));  
                        } else if (isGender) {
                            bounty.setGender(text);
                        }
                    } catch (NumberFormatException e) {
             
                        if (isId || isBounty || isAge) {
                            if (isId) {
                                bounty.setId(0);
                            } else if (isBounty) {
                                bounty.setBounty(0);
                            } else if (isAge) {
                                bounty.setAge(0);
                            }
                        }
                    }
                }
            }

            eventType = parser.next();  
        }

        return bountys;
    }
}
