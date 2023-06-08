# bb-pod-datahike-demo
A demo for using datahike pod in babashka for a lightning talk at 1st babashka-conf 2023

## Talk
- Martin Kleppmann's talk ['Samza and the Unix philosophy of distributed systems'](https://martin.kleppmann.com/2015/08/05/samza-unix-philosophy-at-huguk.html)
## Run
Get native-image from Datahike and run the pod:
```bash
cat access.log | awk '{print $6, $7}' | sort | uniq -c  | sort -r -n | head -n 500 | bb lightning.clj
```
