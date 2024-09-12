var dragAndDropListener = null;

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
    if (data.items && data.items.length > 0) {
        const file = data.items[0].getAsFile();
        console.log(file);
        return file;
    } else if (data.files && data.files.length > 0) {
        const file = data.files[0];
        console.log(file);
        return data.files[0];
    } else {
        console.log("No files found in %", data)
        return undefined;
    }
}