package com.lettuce.demo.base;

    public class demo {
        private static final int cycle=1024,tick = 256;
        public static void main (String[] args) throws InterruptedException {
            for(int i = 0;;i++){
                work(calcNextSleep(i % cycle));
                sleep(tick - calcNextSleep(i % cycle));
            }
        }

        private static long calcNextSleep(long i){
            return (int)(Math.sin((double)i * 2 * Math.PI / cycle) * tick + tick) / 2;
        }

        private static void sleep (long sleepTime) throws InterruptedException
        {
            if(sleepTime < 2)
                Thread.yield();
            else
                Thread.sleep(sleepTime);
        }

        private static void work (long period) {
            long start = System.currentTimeMillis();
            for(;;){
                Math.sin(1);
                if(System.currentTimeMillis() - start >= period)
                    break;
            }
        }
    }

