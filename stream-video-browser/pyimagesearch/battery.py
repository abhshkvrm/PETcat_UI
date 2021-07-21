import wmi

def fun():
    c = wmi.WMI()
    t = wmi.WMI(moniker = "//./root/wmi")


    batts = t.ExecQuery('Select * from BatteryFullChargedCapacity')
    for i, b in enumerate(batts):
        x=b.FullChargedCapacity
    

    batts = t.ExecQuery('Select * from BatteryStatus where Voltage > 0')
    for i, b in enumerate(batts):
        y=b.RemainingCapacity
        z=round(y*100/x,2)
        return z


