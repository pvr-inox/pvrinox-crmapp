package com.cinema.crm.modules.utils;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Utilities {
    private static final String[] IP_HEADERS = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"

            // you can add more matching headers here ...
    };
    Logger logger = LoggerFactory.getLogger("ws");
   // @Value("${environment.otp}")
    private boolean prodOtp = false;

    public static <T> List<List<T>> getBatches(List<T> collection, int batchSize) {
        return IntStream.iterate(0, i -> i < collection.size(), i -> i + batchSize)
                .mapToObj(i -> collection.subList(i, Math.min(i + batchSize, collection.size())))
                .collect(Collectors.toList());
    }

    public String getRequestIP(HttpServletRequest request) {
        for (String header : IP_HEADERS) {
            String value = request.getHeader(header);
            if (value == null || value.isEmpty()) {
                continue;
            }
            String[] parts = value.split("\\s*,\\s*");
            return parts[0];
        }
        return request.getRemoteAddr();
    }

    public String error(Exception e) {
        final StringBuffer bf = new StringBuffer();
        bf.append(e.toString());
        for (int i = 0; i < e.getStackTrace().length; i++) {
            bf.append("\n").append(e.getStackTrace()[i].toString());
        }
        bf.append("\n").append(new Date());
        return ":: ERROR::" + bf;
    }

    public static String stackTrace(StackTraceElement[] stackTrace ) {
        final StringBuffer bf = new StringBuffer();
        for (int i = 0; i <stackTrace.length; i++) {
            bf.append("\n").append(stackTrace[i].toString());
        }
        bf.append("\n").append(new Date());
        return ":: ERROR::" + bf;
    }

    public void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public String generateRandomNumber(int length) {
        String randomNumber = "111111";
        if (prodOtp) {
            randomNumber = RandomStringUtils.randomNumeric(length);
        }
        return randomNumber;
    }

    public int getNumOfMin(Date dateOne, Date dateTwo) {
        final Calendar calendar1 = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(dateOne);
        calendar2.setTime(dateTwo);
        final long milliseconds1 = calendar1.getTimeInMillis();
        final long milliseconds2 = calendar2.getTimeInMillis();
        final long diff = milliseconds2 - milliseconds1;
        // long diffSeconds = diff / 1000;
        final long diffMinutes = diff / (60 * 1000);
        // long diffHours = diff / (60 * 60 * 1000);
        // long diffDays = diff / (24 * 60 * 60 * 1000);
        return (int) diffMinutes;
    }

    //OLD///
    public String getTimeDiff(Date dateOne, Date dateTwo) {
        final Calendar calendar1 = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(dateOne);
        calendar2.setTime(dateTwo);
        final long milliseconds1 = calendar1.getTimeInMillis();
        final long milliseconds2 = calendar2.getTimeInMillis();
        final long diff = milliseconds2 - milliseconds1;
        final long diffSeconds = diff / 1000;
        final long diffMinutes = diff / (60 * 1000);
        final long diffHours = diff / (60 * 60 * 1000);
        final long diffDays = diff / (24 * 60 * 60 * 1000);
        // slf4jLogger.debug(this.getClass().getName()+"\nThe Date Different Example");
        // slf4jLogger.debug(this.getClass().getName()+"Time in milliseconds: " + diff +
        // " milliseconds.");
        // slf4jLogger.debug(this.getClass().getName()+"Time in seconds: " + diffSeconds
        // + " seconds.");
        // slf4jLogger.debug(this.getClass().getName()+"Time in minutes: " + diffMinutes
        // + " minutes.");
        // slf4jLogger.debug(this.getClass().getName()+"Time in hours: " + diffHours + "
        // hours.");
        // slf4jLogger.debug(this.getClass().getName()+"Time in days: " + diffDays + "
        // days.");
        if (diffMinutes == 0) {
            return diffSeconds + " secs ago";
        }
        if (diffHours == 0) {
            return diffMinutes + " min(s) ago";
        } else if (diffHours <= 24) {
            return diffHours + " hr(s) ago";
        } else {
            return diffDays + " day(s) ago";
        }

    }

    public String GetWeekDayName(String strDate) {
        String dayname = null;
        // 25 Apr 2011 9:00 AM
        // sdf = new SimpleDateFormat("dd MMM yyyy");
        final String[] splitarr = strDate.split(" ");
        final String dateOrg = splitarr[0] + " " + splitarr[1] + " " + splitarr[2];
        final String dateString = splitarr[0] + " " + getMonthForString(splitarr[1]) + " " + splitarr[2];
        // slf4jLogger.debug(this.getClass().getName()+"Org Date " + dateOrg);
        final Date currdate = new Date();
        String currStr = null;
        final DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        currStr = formatter.format(currdate);
        // slf4jLogger.debug(this.getClass().getName()+"Curr Date " + currStr);
        // boolean x=currStr.contains(dateOrg);
        // slf4jLogger.debug(this.getClass().getName()+"int value is:"+x);
        if (currStr.contains(dateOrg)) {

            dayname = "Today";
            // slf4jLogger.debug(this.getClass().getName()+"day " + dayname);
        } else {
            Date dateL = null;
            SimpleDateFormat l1;
            l1 = new SimpleDateFormat("dd MM yyyy");
            try {
                dateL = l1.parse(dateString);
                final String teststr = dateL.toString();
                final String[] testArr = teststr.split(" ");
                dayname = testArr[0];
                // slf4jLogger.debug(this.getClass().getName()+"day " + dayname);
            } catch (final ParseException e1) {
                e1.printStackTrace();
            }

        }
        return dayname;
    }

    public int getMonthForString(String mname) {
        final DateFormatSymbols dfs = new DateFormatSymbols();

        int retval = 0;
        final String[] months = dfs.getShortMonths();
        int i = 0;
        while (i < months.length) {
            if (mname.equalsIgnoreCase(months[i])) {
                retval = i;
                // slf4jLogger.debug(this.getClass().getName()+"num "+retval);
                // slf4jLogger.debug(this.getClass().getName()+"month "+months[i]);
                break;
            }

            i++;
        }

        return retval + 1;
    }

    public int getNumOfDays(Date dateOne, Date dateTwo) {
        final Calendar calendar1 = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(dateOne);
        calendar2.setTime(dateTwo);
        final long milliseconds1 = calendar1.getTimeInMillis();
        final long milliseconds2 = calendar2.getTimeInMillis();
        final long diff = milliseconds2 - milliseconds1;
        // long diffSeconds = diff / 1000;
        // long diffMinutes = diff / (60 * 1000);
        // long diffHours = diff / (60 * 60 * 1000);
        final long diffDays = diff / (24 * 60 * 60 * 1000);
        return (int) diffDays + 1;
    }

    public int calcDistance(String latA1, String longA1, String latB2, String longB2) {
        final double latA = Double.parseDouble(latA1);
        final double longA = Double.parseDouble(longA1);
        final double latB = Double.parseDouble(latB2);
        final double longB = Double.parseDouble(longB2);

        double theDistance = (Math.sin(Math.toRadians(latA)) * Math.sin(Math.toRadians(latB))
                + Math.cos(Math.toRadians(latA)) * Math.cos(Math.toRadians(latB))
                * Math.cos(Math.toRadians(longA - longB)));
        theDistance = Double.valueOf((Math.toDegrees(Math.acos(theDistance))) * 69.09 * 1.609344);

        return (int) (theDistance * 1000);
    }

    public long getTimeDiffInHrs(Date dateOne, Date dateTwo) {
        final Calendar calendar1 = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(dateOne);
        calendar2.setTime(dateTwo);
        final long milliseconds1 = calendar1.getTimeInMillis();
        final long milliseconds2 = calendar2.getTimeInMillis();
        final long diff = milliseconds2 - milliseconds1;
        /*
         * long diffSeconds = diff / 1000; long diffMinutes = diff / (60 * 1000);
         */
        final long diffHours = diff / (60 * 60 * 1000);
        /* long diffDays = diff / (24 * 60 * 60 * 1000); */
        return diffHours;

    }

    public long getTimeDiffInMinutes(Date dateOne, Date dateTwo) {
        final Calendar calendar1 = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(dateOne);
        calendar2.setTime(dateTwo);
        final long milliseconds1 = calendar1.getTimeInMillis();
        final long milliseconds2 = calendar2.getTimeInMillis();
        final long diff = milliseconds2 - milliseconds1;
        // long diffSeconds = diff / 1000;
        final long diffMinutes = diff / (60 * 1000);
        // long diffHours = diff / (60 * 60 * 1000);
        /* long diffDays = diff / (24 * 60 * 60 * 1000); */
        return diffMinutes;

    }

    public long getTimeDiffInSeconds(Date dateOne, Date dateTwo) {
        final Calendar calendar1 = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(dateOne);
        calendar2.setTime(dateTwo);
        final long milliseconds1 = calendar1.getTimeInMillis();
        final long milliseconds2 = calendar2.getTimeInMillis();
        final long diff = milliseconds2 - milliseconds1;
         long diffSeconds = diff;
//        final long diffMinutes = diff / (60 * 1000);
        // long diffHours = diff / (60 * 60 * 1000);
        /* long diffDays = diff / (24 * 60 * 60 * 1000); */
        return diffSeconds;

    }


    public String getReleaseTxt(Date openingDate) {
        try {
            String rt = "";
            new SimpleDateFormat("EEEE");
            new SimpleDateFormat("dd-MMM-yyyy");
            final DateFormat dpdf = new SimpleDateFormat("EEE MMM dd");
            if (openingDate.after(new Date())) {
                // if(getNumOfDays(new Date(),openingDate)<7)
                // rt = "Opens "+sdf.format(openingDate);
                // else
                rt = "Releasing on " + dpdf.format(openingDate);
            } else if (openingDate.before(new Date()) && getNumOfDays(openingDate, new Date()) < 7) {
                rt = "New Release";
            }
            // else {
            // rt = "Releases on "+dpdf.format(openingDate);
            // }
            return rt;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "";
    }
//
//	public TheaterVO getTheaterCloneObj(TheaterVO theaterVO) {
//		final TheaterVO theaterVO2 = new TheaterVO();
//		theaterVO2.setCinemaId(theaterVO.getCinemaId());
//		theaterVO2.setCinemaName(theaterVO.getCinemaName());
//		theaterVO2.setConvFee(theaterVO.getConvFee());
//		theaterVO2.setDistance(theaterVO.getDistance());
//		theaterVO2.setLatitude(theaterVO.getLatitude());
//		theaterVO2.setLongitude(theaterVO.getLongitude());
//		theaterVO2.setSubcity(theaterVO.getSubcity());
//		theaterVO2.setIfdolby(theaterVO.isIfdolby());
//		// theaterVO2.setShowsVOs(theaterVO.getShowsVOs());
//		return theaterVO2;
//	}

    public String getMovieLength(Short allmin) {
        if (allmin != null) {
            String lengStr = "";
            final int hrs = allmin / 60;
            if (hrs > 0) {
                lengStr = hrs + "h ";
            }
            final int minutes = allmin % 60;
            if (minutes > 0) {
                lengStr += minutes + "m";
            }
            return lengStr;
        } else {
            return "";
        }

    }
//
//	public Film2VO getFilm2CloneObj(Film2VO filmDetails) {
//		final Film2VO film2vo = new Film2VO(filmDetails.getMcode(),
//				filmDetails.getMname().replace("(", "").replace(")", ""), filmDetails.getMgenre(),
//				filmDetails.getMopeningdate(), filmDetails.getMcensor(), filmDetails.getMlength(),
//				filmDetails.getMlanguage());
//		film2vo.setMtype(filmDetails.getMtype());
//		return film2vo;
//	}

    public long getTimeDiffInInt(Date dateOne, Date dateTwo) {
        final Calendar calendar1 = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(dateOne);
        calendar2.setTime(dateTwo);
        final long milliseconds1 = calendar1.getTimeInMillis();
        final long milliseconds2 = calendar2.getTimeInMillis();
        final long diff = milliseconds2 - milliseconds1;
        /*
         * long diffSeconds = diff / 1000; long diffMinutes = diff / (60 * 1000);
         */
        final long diffHours = diff / (60 * 60 * 1000);
        /* long diffDays = diff / (24 * 60 * 60 * 1000); */
        return diffHours;

    }

    public String getInIdsForQuery(String strcodes) {
        final String[] codes = strcodes.split(",");
        String ids = "";
        for (int i = 0; i < codes.length; i++) {
            ids += "'";
            ids += codes[i];
            ids += "'";
            if (i != codes.length - 1) {
                ids += ",";
            }
        }
        return ids;
    }

    public String getReleaseTxt2(Date openingDate) {
        final int days = getNumOfDays(new Date(), openingDate);
        if (days > 1 && days < 20) {
            return "Releasing in " + (days - 1) + " days";
        } else if (days > 20) {
            final int weeks = days / 7;
            if (weeks <= 4) {
                return "1 month to go";
            } else {
                final int month = days / 30;
                if (month <= 3) {
                    return month + " month(s) to go";
                } else {
                    return "";
                }
            }
        }
        return "";
    }

    public String longListToString(List<Long> list) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(list.get(i));
        }

        return sb.toString();
    }

    public List<Long> stringToLongList(String longListStr) {
        List<Long> longList = new ArrayList<>();
        String[] stringArray = longListStr.split(",");

        for (String str : stringArray) {
            longList.add(Long.parseLong(str));
        }

        return longList;
    }

    public Date getBussinessDate(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int day_of_year = cal.get(Calendar.DAY_OF_YEAR);
        if ((cal.get(Calendar.HOUR_OF_DAY)) >= 0 && (cal.get(Calendar.HOUR_OF_DAY)) < 6) {
            cal.set(Calendar.DAY_OF_YEAR, day_of_year - 1);
        }
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 00);
        return cal.getTime();
    }

    public String createBookingId(String ccode, long intBookId) {
        String bookingId = String.valueOf(intBookId);
        for (int i = bookingId.length(); i < 10; i++) {
            bookingId = "0" + bookingId;
        }
        DateFormat sdf = new SimpleDateFormat("yy");
        bookingId = ccode + sdf.format(new Date()) + bookingId;
        return bookingId;
    }

   

    public String calculateSHA256(String binNo) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(binNo.getBytes());

        // Convert the byte array to a hexadecimal representation
        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public String toCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Convert to camelCase with the first letter capitalized
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public String getScreenType(String screenType, String filmFormat) {
        filmFormat = filmFormat.toUpperCase(Locale.ROOT);
//        if (filmFormat.contains("ATMOS")) {
//            return "ATMOS";
//        } else
        if (filmFormat.contains("MX4D")) {
            return "MX4D";
        } else if (filmFormat.contains("4DX")) {
            return "4DX";
        } else if (filmFormat.contains("SCREEN X")) {
            return "SCREEN X";
        } else if (screenType.trim().equalsIgnoreCase("PREMIUM")) {
            return "";
        } else {
            return screenType;
        }
    }

    public String getFilmFormat(String filmFormat) {
        filmFormat = filmFormat.toUpperCase(Locale.ROOT);
        if (filmFormat.contains("ATMOS")) {
            filmFormat = filmFormat.replace("ATMOS", "");
            return filmFormat.trim();
        } else if (filmFormat.contains("MX4D")) {
            filmFormat = filmFormat.replace("MX4D", "");
            return filmFormat.trim();
        } else if (filmFormat.contains("4DX")) {
            filmFormat = filmFormat.replace("4DX", "");
            return filmFormat.trim();
        } else if (filmFormat.contains("SCREEN X")) {
            filmFormat = filmFormat.replace("SCREEN X", "");
            return filmFormat.trim();
        } else {
            return filmFormat;
        }
    }

    public String getSoundFormat(String filmFormat) {
        filmFormat = filmFormat.toUpperCase(Locale.ROOT);
        if (filmFormat.contains("ATMOS")) {
            return "ATMOS";
        } else {
            return "";
        }
    }
}
