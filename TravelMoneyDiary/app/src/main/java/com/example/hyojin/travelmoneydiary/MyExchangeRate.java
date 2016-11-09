package com.example.hyojin.travelmoneydiary;

import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hyojin on 2016-11-09.
 */

public class MyExchangeRate {

    int beforeMoney ;
    int afterMoney ;

    float[] exchangeRate = new float[12] ; // 각 나라의 환율

    void setExcahngeRate() {
        new Thread() {
            public void run() {
                try {
                    String Address = "http://fx.kebhana.com/fxportal/jsp/RS/DEPLOY_EXRATE/fxrate_all.html";        //자료를 받아올 주소
                    URL url = new URL(Address);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();        //연결해줌;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "MS949")); //MS949는 한글표현;
                    String protocol = "GET";
                    conn.setRequestMethod(protocol);        //언어타입과 문자타입지정

                    int i = 0 ;

                    String line;
                    String tempLine;

                    while ((line = br.readLine()) != null) {//웹에서 소스보기를 통하여 원하는 부분의 데이터를 받아옴
                        if (line.startsWith("<td class='buy'>")) { // 환율 받기 1
                            tempLine = line.replace("<td class='buy'>", "").replace("</td>", "");
                            exchangeRate[i] = Float.parseFloat(tempLine);
                            i++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    void setBeforeMoney(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            beforeMoney = 0 ;
        } else {
            beforeMoney = Integer.parseInt(editText.getText().toString());
        }
    }

    void seeAfterMoney(int numCountry, EditText editText, TextView textView){
        setExcahngeRate();
        setBeforeMoney(editText);

        switch (numCountry) {
            case 0:
                afterMoney = beforeMoney * (int)exchangeRate[4] ;
                break;
            case 1:
                afterMoney = beforeMoney * (int)exchangeRate[5] ;
                break ;
            case 2:
                afterMoney = beforeMoney * (int)exchangeRate[6] ;
                break ;
            case 3:
                afterMoney = beforeMoney * (int)exchangeRate[7] ;
                break ;
            case 4:
                afterMoney = beforeMoney ;
                break ;
        }

        textView.setText(Integer.toString(afterMoney));
    }
}
