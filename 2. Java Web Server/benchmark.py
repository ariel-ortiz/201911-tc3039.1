from subprocess import Popen

n = 20
processes = []

for i in range(n):
    processes.append(Popen(['curl', 'http://localhost:12345']))

for p in processes:
    p.wait()
