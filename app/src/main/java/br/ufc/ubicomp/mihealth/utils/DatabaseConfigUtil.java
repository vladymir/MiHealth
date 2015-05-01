package br.ufc.ubicomp.mihealth.utils;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Created by vladymirbezerra on 27/04/15.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt");
    }
}
