package id.com.ervsoftware.ysl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Setting {

    // YSL
    public static final String IP = "http://119.235.208.235:8092/";

    public static final String URL_Privacy_Policy=IP+"privacy_policy";

    public static final String API = IP+"api/";

    public static final String API_Login = API + "auth/login";
    public static final String API_Email = API + "email";

    public static final String API_Profile_Forgot = API + "profile-forgot";
    public static final String API_Piutang_Dagang = API + "piutang_dagang_per_customer";
    public static final String API_Hutang_Dagang = API + "hutang_dagang_per_supplier";
    public static final String API_Pembelian_Dagang = API + "pembelian_per_supplier";
    public static final String API_Penjualan_Dagang = API + "penjualan_per_customer";
    public static final String API_Informasi_Supplier = API + "info_per_supplier";
    public static final String API_Informasi_Customer = API + "info_per_customer";
    public static final String API_Profile_Supplier = API + "profile_supplier";
    public static final String API_Profile_Supplier_Details = API + "profile_supplier_detail";
    public static final String API_Profile_Customer = API + "profile_customer";
    public static final String API_Profile_Customer_Details = API + "profile_customer_detail";
    public static final String API_Jatuh_Tempo_Piutang = API + "piutang_dagang_per_invoice?";
    public static final String API_Jatuh_Tempo_Hutang = API + "hutang_dagang_per_invoice?";


    //shared preference
    public static String SP_TOKEN = "user_token";
    public static String SP_USER = "user_username";
    public static String SP_EMAIL = "user_email";
    public static String SP_NO_HP = "user_email";

    //Parameter
    public static String SELECTED_NAME = "";
        //Default date
        public static String thisYear = new SimpleDateFormat("yyyy").format(new Date());
        public static String thisMonth = new SimpleDateFormat("MM").format(new Date());

    public static String FROM_DATE = thisYear + "01";
    public static String TO_DATE = thisYear + thisMonth;
    public static int PER_BULAN;
    public static int SET_HARI = 30;
    public static int STATE_JATUH_TEMPO;

    //Display date
    public static String DISPLAY_FROM_DATE = "01/" + thisYear;
    public static String DISPLAY_TO_DATE = thisMonth + "/" + thisYear;
    public static String DISPLAY_PERIODE = "Jan " + thisYear + " - " + new SimpleDateFormat("MMM").format(new Date()) + " " + thisYear;

    //Pemisah ribuan
    public static String pemisahRibuan(int value){
        DecimalFormat pemisahRibuan = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatPemisah = new DecimalFormatSymbols();

        formatPemisah.setCurrencySymbol("");
        formatPemisah.setMonetaryDecimalSeparator(',');
        formatPemisah.setGroupingSeparator('.');

        pemisahRibuan.setDecimalFormatSymbols(formatPemisah);

        return pemisahRibuan.format(value);
    }
    public static String pemisahRibuan(Long value){
        DecimalFormat pemisahRibuan = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatPemisah = new DecimalFormatSymbols();

        formatPemisah.setCurrencySymbol("");
        formatPemisah.setMonetaryDecimalSeparator(',');
        formatPemisah.setGroupingSeparator('.');

        pemisahRibuan.setDecimalFormatSymbols(formatPemisah);

        return pemisahRibuan.format(value);
    }
    public static String pemisahRibuan(Float value){
        DecimalFormat pemisahRibuan = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatPemisah = new DecimalFormatSymbols();

        formatPemisah.setCurrencySymbol("");
        formatPemisah.setMonetaryDecimalSeparator(',');
        formatPemisah.setGroupingSeparator('.');

        pemisahRibuan.setDecimalFormatSymbols(formatPemisah);

        return pemisahRibuan.format(value);
    }

    //Date Formatter
    public static String dateFormatter(String date){
        String result = null;
        DateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat outFormat = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date dateFormat = inFormat.parse(date);
            result = outFormat.format(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outFormat.format(date);
    }

    //Jatuh Tempo
    public static String jtUrl = "";
    public static String jtParam = "";
    public static String jtCode = "";
    public static String jtName = "";
    public static Boolean jtChecked = false;
}