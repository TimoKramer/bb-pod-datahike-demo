# bb-pod-datahike-demo
A demo for using datahike pod in babashka for a lightning talk at 1st babashka-conf 2023

## Talk
- Inspired by Martin Kleppmann's talk ['Samza and the Unix philosophy of distributed systems'](https://martin.kleppmann.com/2015/08/05/samza-unix-philosophy-at-huguk.html)
- Unix tools combine well
- Philosophy is to do one thing well
- Pipelines connect the tools' outputs
- Babashka can be one of the tools in a unix pipeline
- Especially storing data in a datalog database with durability is now possible
- Still working on it for different backends

## Run

Get native-image from Datahike and run the pod:
```bash
cat access.log | awk '{print $6, $7}' | sort | uniq -c  | sort -r -n | head -n 500 | bb lightning.clj
```
