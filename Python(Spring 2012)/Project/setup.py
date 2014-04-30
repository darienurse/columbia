from cx_Freeze import setup,Executable

includefiles = ['addMoney.gif','addMoney2.gif', 'all.gif', 'all2.gif', 'back.gif','biweekly.gif','money1.gif','money2.gif','money3.gif','money4.gif','money5.gif','money6.gif','money7.gif','money8.gif', 'monthly.gif','next.gif','one-day.gif','opt.gif','previous.gif','regular.gif','star.gif','startover.gif','submit.gif','wait.gif','weekly.gif']
includes = []
excludes = ['Tkinter']
packages = ['Bank','Card']


setup(
    name = 'Metro Card Machine',
    version = '1.0',
    description = 'A MTA machine simulation',
    author = 'Darien Nurse & Janee Benjamin',
    author_email = 'darienurse@gmail.com',
    options = {'build_exe': {'excludes':excludes,'packages':packages,'include_files':includefiles}}, 
    executables = [Executable('MTA Machine.py', icon='all.ico')]
)