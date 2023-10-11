# Playground

## Quick Start

Start a Postgres at port 5432.

```shell
# Step 1: create the database `playgroud`
./bootstrap.sh

# Step 2: run the test
./gradlew bootRun

# Checkout the logs for test results

# Change TestRunners in TestConfiguration.java and Qualifier of JobExecutor in TestRunner
# to try different locks.
```

### Notes
   1. These *_logs files at the root of the repo contain outputs from the tests. They are just for
      you to read and not used by anything. You can clean them up to free disk space.
   
   2. There is a 20ms delay in each batch processing.

### Results

#### 1000 tasks, 10 tasks per batch, 2 threads

```
Custom advisory lock
Count of tasks completed by thread1: 1000
Count of tasks completed by thread2: 0
Total time: 13792ms

Postgre advisory lock
Count of tasks completed by thread1: 530
Count of tasks completed by thread2: 470
Total time: 12250ms

Postgres Row lock
Count of tasks completed by thread1: 500
Count of tasks completed by thread2: 500
Total time: 6394ms
```

#### 1000 tasks, 5 tasks per batch, 2 threads

```
Custom advisory lock
Count of tasks completed by thread1: 1000
Count of tasks completed by thread2: 0
Total time: 25263ms

Postgre advisory lock
Count of tasks completed by thread1: 505
Count of tasks completed by thread2: 495
Total time: 22793ms

Postgres Row lock
Count of tasks completed by thread1: 500
Count of tasks completed by thread2: 500
Total time: 11917ms
```

#### 1000 tasks, 5 tasks per batch, 4 threads

```
Custom advisory lock
Count of tasks completed by thread1: 1000
Count of tasks completed by thread2: 0
Count of tasks completed by thread3: 0
Count of tasks completed by thread4: 0
Total time: 26276ms

Postgre advisory lock
Count of tasks completed by thread1: 285
Count of tasks completed by thread2: 245
Count of tasks completed by thread3: 245
Count of tasks completed by thread4: 245
Total time: 23468ms

Postgres Row lock
Count of tasks completed by thread1: 250
Count of tasks completed by thread2: 250
Count of tasks completed by thread3: 250
Count of tasks completed by thread4: 250
Total time: 6102ms
```

There is not much different between the total time of Custom advisory lock and Postgres advisory lock.
Postgres advisory lock distribute tasks more event across all threads.

Postgres row lock is definitely a winner.

The results may not reflect performance in the real world for each lock. Tasks run continuously which is
quite different from real world usages.