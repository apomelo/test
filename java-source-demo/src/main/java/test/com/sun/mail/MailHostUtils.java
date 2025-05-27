package test.com.sun.mail;

import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.*;

public class MailHostUtils {
    private static Set<String> validMailHost = new HashSet<>();
    private static LinkedHashMap<String, Integer> mailHostCache = createLRUMap(1024);

    static {
        String hosts = "126.com,zwoho.com,mail.ru,gmx.net,inbox.ru,live.com,googlemail.com,msn.com,hotmail.co.uk,outlook.com,gmail.com,yahoo.fr,gamil.com,hotmail.fr,sina.com,web.de,llllll.com,email.com,yahoo.com.tw,t-online.de,icloud.com,yahoo.com,yahoo.com.vn,o2.pl,qq.com,hotmail.it,libero.it,gmai.com,yandex.ru,139.com,aol.com,ya.ru,rambler.ru,yahoo.co.uk,outlook.fr,163.com,gmx.de,yyyyyy.com,z.com,meta.ua,i.ua,guin.com,hhhhhh.com,me.com,mmmmmm.com,list.ru,bk.ru,hanmail.net,gmil.com,abv.bg,wp.pl,inbox.lv,mail.com,interia.pl,sohu.com,yahoo.de,hotmail.de,naver.com,ymail.com,seznam.cz,ukr.net,hotmail.com";
        for (String h : hosts.split(",")) {
            validMailHost.add(h);
        }
    }

    private static <K, V> LinkedHashMap<K, V> createLRUMap(final int maxEntries) {
        return new LinkedHashMap<K, V>(maxEntries * 10 / 7, 0.7f, true) {
            private static final long serialVersionUID = 8029465752547558722L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxEntries;
            }
        };
    }

    public static boolean isMailHostValid(String mailHost) {
        String hostname = mailHost.toLowerCase();
        if (validMailHost.contains(hostname)) {
            return true;
        }

        Integer state = mailHostCache.get(hostname);
        if (state != null) {
            return state == 1;
        }

        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        try {
            DirContext ictx = new InitialDirContext(env);
            Attributes attrs = ictx.getAttributes(hostname, new String[] { "MX" });
            if (attrs.get("MX") != null) {
                mailHostCache.put(hostname, 1);
                return true;
            }
        } catch (Exception ex) {
        }
        mailHostCache.put(hostname, 0);
        return false;
    }
}
