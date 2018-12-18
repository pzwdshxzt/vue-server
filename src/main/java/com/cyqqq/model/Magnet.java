package com.cyqqq.model;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.turn.ttorrent.common.Torrent;

/**
 * Description
 * 磁力
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/12/11 16:11
 * @Version :
 */
public class Magnet {

    public String infoHash = null;
    public String name = null;
    public List<URI> announce = null;

    public Magnet load(Torrent torrent) throws UnsupportedEncodingException {
        infoHash = torrent.getHexInfoHash();
        name = torrent.getName();
        announce = new ArrayList<>();
        for (List<URI> uris : torrent.getAnnounceList()) {
            for (URI uri : uris) {
                announce.add(uri);
            }
        }
        return this;
    }

    public Torrent createTorrent() {
        //TODO:
        return null;
    }

    public Magnet parse(String magnetUri) throws ParseException, UnsupportedEncodingException, URISyntaxException {
        if (!magnetUri.startsWith("magnet:?")){
            throw new ParseException(magnetUri, 0);
        }
        magnetUri = magnetUri.substring(8);
        String[] args = magnetUri.split("&");
        for (String arg : args) {
            if (arg.startsWith("xt=urn:btih:")) {
                infoHash = arg.substring(12);
            } else if (arg.startsWith("dn=")) {
                name = URLDecoder.decode(arg.substring(3), "UTF-8");
            } else if (arg.startsWith("tr=")) {
                addAnnounce(new URI(URLDecoder.decode(arg.substring(3), "UTF-8")));
            }
        }
        return this;
    }

    public void addAnnounce(URI uri) {
        if (announce == null) {
            announce = new ArrayList<>();
        }
        announce.add(uri);
    }

    public String write() throws UnsupportedEncodingException {
        String magnet = "magnet:?xt=urn:btih:" + infoHash
                + "&dn=" + URLEncoder.encode(name, "UTF-8");
        for (URI uri : announce) {
            magnet += "&tr=" + URLEncoder.encode(uri.toString(), "UTF-8");
        }
        return magnet;
    }

    public static void main(String[] args) {

        Magnet parse = null;
        try {
            parse = new Magnet().parse("magnet:?xt=urn:btih:786588980c525e03d09382238498df7e236d6de0");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        for (URI uri:parse.announce){
            System.out.println(uri.getPath());
        }
    }


}
