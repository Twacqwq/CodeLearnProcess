const { contextBridge, ipcRenderer } = require('electron');
const handleSend = async () => {
    let fallback = await ipcRenderer.invoke('send-event', "hahahahahha");
    console.log(fallback);
}

contextBridge.exposeInMainWorld('myApi', {
    platform: process.platform,
    handleSend
})