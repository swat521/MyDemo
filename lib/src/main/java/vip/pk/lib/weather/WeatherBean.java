package vip.pk.lib.weather;

public class WeatherBean {


    /**
     * resultcode : 200
     * reason : 查询成功
     * result : {"sk":{"temp":"22","wind_direction":"东风","wind_strength":"2级","humidity":"69%","time":"09:10"},"today":{"temperature":"21℃~28℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"北风微风","week":"星期三","city":"长沙","date_y":"2018年05月09日","dressing_index":"热","dressing_advice":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。","uv_index":"弱","comfort_index":"","wash_index":"较适宜","travel_index":"适宜","exercise_index":"适宜","drying_index":""},"future":{"day_20180509":{"temperature":"21℃~28℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"北风微风","week":"星期三","date":"20180509"},"day_20180510":{"temperature":"23℃~27℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"南风微风","week":"星期四","date":"20180510"},"day_20180511":{"temperature":"23℃~30℃","weather":"阵雨","weather_id":{"fa":"03","fb":"03"},"wind":"南风微风","week":"星期五","date":"20180511"},"day_20180512":{"temperature":"23℃~26℃","weather":"阵雨","weather_id":{"fa":"03","fb":"03"},"wind":"南风微风","week":"星期六","date":"20180512"},"day_20180513":{"temperature":"25℃~32℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"南风微风","week":"星期日","date":"20180513"},"day_20180514":{"temperature":"23℃~26℃","weather":"阵雨","weather_id":{"fa":"03","fb":"03"},"wind":"南风微风","week":"星期一","date":"20180514"},"day_20180515":{"temperature":"23℃~26℃","weather":"阵雨","weather_id":{"fa":"03","fb":"03"},"wind":"南风微风","week":"星期二","date":"20180515"}}}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * sk : {"temp":"22","wind_direction":"东风","wind_strength":"2级","humidity":"69%","time":"09:10"}
         * today : {"temperature":"21℃~28℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"北风微风","week":"星期三","city":"长沙","date_y":"2018年05月09日","dressing_index":"热","dressing_advice":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。","uv_index":"弱","comfort_index":"","wash_index":"较适宜","travel_index":"适宜","exercise_index":"适宜","drying_index":""}
         * future : {"day_20180509":{"temperature":"21℃~28℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"北风微风","week":"星期三","date":"20180509"},"day_20180510":{"temperature":"23℃~27℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"南风微风","week":"星期四","date":"20180510"},"day_20180511":{"temperature":"23℃~30℃","weather":"阵雨","weather_id":{"fa":"03","fb":"03"},"wind":"南风微风","week":"星期五","date":"20180511"},"day_20180512":{"temperature":"23℃~26℃","weather":"阵雨","weather_id":{"fa":"03","fb":"03"},"wind":"南风微风","week":"星期六","date":"20180512"},"day_20180513":{"temperature":"25℃~32℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"南风微风","week":"星期日","date":"20180513"},"day_20180514":{"temperature":"23℃~26℃","weather":"阵雨","weather_id":{"fa":"03","fb":"03"},"wind":"南风微风","week":"星期一","date":"20180514"},"day_20180515":{"temperature":"23℃~26℃","weather":"阵雨","weather_id":{"fa":"03","fb":"03"},"wind":"南风微风","week":"星期二","date":"20180515"}}
         */

        private SkBean sk;
        private TodayBean today;
        private FutureBean future;

        public SkBean getSk() {
            return sk;
        }

        public void setSk(SkBean sk) {
            this.sk = sk;
        }

        public TodayBean getToday() {
            return today;
        }

        public void setToday(TodayBean today) {
            this.today = today;
        }

        public FutureBean getFuture() {
            return future;
        }

        public void setFuture(FutureBean future) {
            this.future = future;
        }

        public static class SkBean {
            /**
             * temp : 22
             * wind_direction : 东风
             * wind_strength : 2级
             * humidity : 69%
             * time : 09:10
             */

            private String temp;
            private String wind_direction;
            private String wind_strength;
            private String humidity;
            private String time;

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_strength() {
                return wind_strength;
            }

            public void setWind_strength(String wind_strength) {
                this.wind_strength = wind_strength;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class TodayBean {
            /**
             * temperature : 21℃~28℃
             * weather : 多云
             * weather_id : {"fa":"01","fb":"01"}
             * wind : 北风微风
             * week : 星期三
             * city : 长沙
             * date_y : 2018年05月09日
             * dressing_index : 热
             * dressing_advice : 天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。
             * uv_index : 弱
             * comfort_index :
             * wash_index : 较适宜
             * travel_index : 适宜
             * exercise_index : 适宜
             * drying_index :
             */

            private String temperature;
            private String weather;
            private WeatherIdBean weather_id;
            private String wind;
            private String week;
            private String city;
            private String date_y;
            private String dressing_index;
            private String dressing_advice;
            private String uv_index;
            private String comfort_index;
            private String wash_index;
            private String travel_index;
            private String exercise_index;
            private String drying_index;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public WeatherIdBean getWeather_id() {
                return weather_id;
            }

            public void setWeather_id(WeatherIdBean weather_id) {
                this.weather_id = weather_id;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDate_y() {
                return date_y;
            }

            public void setDate_y(String date_y) {
                this.date_y = date_y;
            }

            public String getDressing_index() {
                return dressing_index;
            }

            public void setDressing_index(String dressing_index) {
                this.dressing_index = dressing_index;
            }

            public String getDressing_advice() {
                return dressing_advice;
            }

            public void setDressing_advice(String dressing_advice) {
                this.dressing_advice = dressing_advice;
            }

            public String getUv_index() {
                return uv_index;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public String getComfort_index() {
                return comfort_index;
            }

            public void setComfort_index(String comfort_index) {
                this.comfort_index = comfort_index;
            }

            public String getWash_index() {
                return wash_index;
            }

            public void setWash_index(String wash_index) {
                this.wash_index = wash_index;
            }

            public String getTravel_index() {
                return travel_index;
            }

            public void setTravel_index(String travel_index) {
                this.travel_index = travel_index;
            }

            public String getExercise_index() {
                return exercise_index;
            }

            public void setExercise_index(String exercise_index) {
                this.exercise_index = exercise_index;
            }

            public String getDrying_index() {
                return drying_index;
            }

            public void setDrying_index(String drying_index) {
                this.drying_index = drying_index;
            }

            public static class WeatherIdBean {
                /**
                 * fa : 01
                 * fb : 01
                 */

                private String fa;
                private String fb;

                public String getFa() {
                    return fa;
                }

                public void setFa(String fa) {
                    this.fa = fa;
                }

                public String getFb() {
                    return fb;
                }

                public void setFb(String fb) {
                    this.fb = fb;
                }
            }
        }

        public static class FutureBean {
            /**
             * day_20180509 : {"temperature":"21℃~28℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"北风微风","week":"星期三","date":"20180509"}
             * day_20180510 : {"temperature":"23℃~27℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"南风微风","week":"星期四","date":"20180510"}
             * day_20180511 : {"temperature":"23℃~30℃","weather":"阵雨","weather_id":{"fa":"03","fb":"03"},"wind":"南风微风","week":"星期五","date":"20180511"}
             * day_20180512 : {"temperature":"23℃~26℃","weather":"阵雨","weather_id":{"fa":"03","fb":"03"},"wind":"南风微风","week":"星期六","date":"20180512"}
             * day_20180513 : {"temperature":"25℃~32℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"南风微风","week":"星期日","date":"20180513"}
             * day_20180514 : {"temperature":"23℃~26℃","weather":"阵雨","weather_id":{"fa":"03","fb":"03"},"wind":"南风微风","week":"星期一","date":"20180514"}
             * day_20180515 : {"temperature":"23℃~26℃","weather":"阵雨","weather_id":{"fa":"03","fb":"03"},"wind":"南风微风","week":"星期二","date":"20180515"}
             */

            private Day20180509Bean day_20180509;
            private Day20180510Bean day_20180510;
            private Day20180511Bean day_20180511;
            private Day20180512Bean day_20180512;
            private Day20180513Bean day_20180513;
            private Day20180514Bean day_20180514;
            private Day20180515Bean day_20180515;

            public Day20180509Bean getDay_20180509() {
                return day_20180509;
            }

            public void setDay_20180509(Day20180509Bean day_20180509) {
                this.day_20180509 = day_20180509;
            }

            public Day20180510Bean getDay_20180510() {
                return day_20180510;
            }

            public void setDay_20180510(Day20180510Bean day_20180510) {
                this.day_20180510 = day_20180510;
            }

            public Day20180511Bean getDay_20180511() {
                return day_20180511;
            }

            public void setDay_20180511(Day20180511Bean day_20180511) {
                this.day_20180511 = day_20180511;
            }

            public Day20180512Bean getDay_20180512() {
                return day_20180512;
            }

            public void setDay_20180512(Day20180512Bean day_20180512) {
                this.day_20180512 = day_20180512;
            }

            public Day20180513Bean getDay_20180513() {
                return day_20180513;
            }

            public void setDay_20180513(Day20180513Bean day_20180513) {
                this.day_20180513 = day_20180513;
            }

            public Day20180514Bean getDay_20180514() {
                return day_20180514;
            }

            public void setDay_20180514(Day20180514Bean day_20180514) {
                this.day_20180514 = day_20180514;
            }

            public Day20180515Bean getDay_20180515() {
                return day_20180515;
            }

            public void setDay_20180515(Day20180515Bean day_20180515) {
                this.day_20180515 = day_20180515;
            }

            public static class Day20180509Bean {
                /**
                 * temperature : 21℃~28℃
                 * weather : 多云
                 * weather_id : {"fa":"01","fb":"01"}
                 * wind : 北风微风
                 * week : 星期三
                 * date : 20180509
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanX {
                    /**
                     * fa : 01
                     * fb : 01
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180510Bean {
                /**
                 * temperature : 23℃~27℃
                 * weather : 多云
                 * weather_id : {"fa":"01","fb":"01"}
                 * wind : 南风微风
                 * week : 星期四
                 * date : 20180510
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXX {
                    /**
                     * fa : 01
                     * fb : 01
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180511Bean {
                /**
                 * temperature : 23℃~30℃
                 * weather : 阵雨
                 * weather_id : {"fa":"03","fb":"03"}
                 * wind : 南风微风
                 * week : 星期五
                 * date : 20180511
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXX {
                    /**
                     * fa : 03
                     * fb : 03
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180512Bean {
                /**
                 * temperature : 23℃~26℃
                 * weather : 阵雨
                 * weather_id : {"fa":"03","fb":"03"}
                 * wind : 南风微风
                 * week : 星期六
                 * date : 20180512
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXX {
                    /**
                     * fa : 03
                     * fb : 03
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180513Bean {
                /**
                 * temperature : 25℃~32℃
                 * weather : 多云
                 * weather_id : {"fa":"01","fb":"01"}
                 * wind : 南风微风
                 * week : 星期日
                 * date : 20180513
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXXX {
                    /**
                     * fa : 01
                     * fb : 01
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180514Bean {
                /**
                 * temperature : 23℃~26℃
                 * weather : 阵雨
                 * weather_id : {"fa":"03","fb":"03"}
                 * wind : 南风微风
                 * week : 星期一
                 * date : 20180514
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXXXX {
                    /**
                     * fa : 03
                     * fb : 03
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20180515Bean {
                /**
                 * temperature : 23℃~26℃
                 * weather : 阵雨
                 * weather_id : {"fa":"03","fb":"03"}
                 * wind : 南风微风
                 * week : 星期二
                 * date : 20180515
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXXXXX {
                    /**
                     * fa : 03
                     * fb : 03
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }
        }
    }
}
