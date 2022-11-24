const { app, BrowserWindow, ipcMain, dialog, globalShortcut, Menu } = require('electron');
const path = require('path');

const mainMenu = Menu.buildFromTemplate([
    {
        label: 'electron',
        submenu: [
            {
                label: 'submenu-1',
            },
            {
                label: 'submenu-2',
                click: () => {
                    console.log(233);
                }
            }
        ]
    }
])

const createWindow = () => {
    const win = new BrowserWindow({
        width: 1000,
        height: 800,
        x: 100,
        y: 100,
        show: false,
        webPreferences: {
            preload: path.resolve(__dirname, "./preload.js")
        }
    });
    const wc = win.webContents
    wc.on('context-menu', (e, params) => {
        dialog.showOpenDialog({
            buttonLabel: '选择',
            defaultPath: app.getPath('desktop'),
            properties: ['multiSelections', 'createDirectory', 'openFile', 'openDirectory']
        }).then(result => {
            console.log(result.filePaths);
        })
    })
    win.on('ready-to-show', () => {
        win.show()
    })
    win.loadFile('index.html');
    globalShortcut.register('CommandOrControl+Y', () => {
        console.log('ggg');
    })

    Menu.setApplicationMenu(mainMenu)

    // const win2 = new BrowserWindow({
    //     width: 600,
    //     height: 400,
    //     parent: win,
    //     modal: true,
    // })
    // win2.loadURL('https://www.baidu.com')
}

app.on('window-all-closed', () => {
    if (process.platform === 'darwin') {
        app.quit();
    }
});

// 失去焦点
app.on('browser-window-blur', () => {
    console.log("失去焦点了捏");
})

ipcMain.handle('send-event', (event, msg) => {
    return msg
})

app.whenReady().then(() => {
    createWindow();
    
});