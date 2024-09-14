let dragAndDropListener = null;

function registerDragAndDropListener(onDragOver, onDragLeave, onDrop) {
    dragAndDropListener = {
        onDragOver: onDragOver,
        onDragLeave: onDragLeave,
        onDrop: onDrop,
    };
}

function unregisterDragAndDropListener() {
    dragAndDropListener = null;
}

function dropHandler(ev) {
    ev.preventDefault();
    const files = extractMultiFile(ev);
    if (files !== null && files !== undefined) {
        dragAndDropListener?.onDrop(files);
    }
}

function dragOverHandler(ev) {
    ev.preventDefault();
    dragAndDropListener?.onDragOver();
}

function dragLeaveHandler(ev) {
    ev.preventDefault();
    dragAndDropListener?.onDragLeave();
}

function extractMultiFile(ev) {
    const data = ev.dataTransfer;
    let files = [];
    if (data.files && data.files.length > 0) {
        for (let i = 0; i < data.files.length; i++) {
            files.push(data.files[i])
        }
        return files[0];
    } else if (data.items && data.items.length > 0) {
        for (let i = 0; i < data.items.length; i++) {
            files.push(data.items[i].getAsFile())
        }
        return files[0];
    }
    return undefined;
}