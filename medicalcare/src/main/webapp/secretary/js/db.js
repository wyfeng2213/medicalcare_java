function DBHelper() {
    DBHelper.IsSupport = function () {
        return window.indexedDB;
    };
    DBHelper.CreateDatabase = function (dbName) {
        var openRequest = localDatabase.indexedDB.open(dbName);

        openRequest.onerror = function (e) {
            console.log("Database error: " + e.target.errorCode);
        };
        openRequest.onsuccess = function (event) {
            console.log("Database created");
            localDatabase.db = openRequest.result;
        };
        openRequest.onupgradeneeded = function (evt) {

        };
    };
    DBHelper.DeleteDatabase= function() {
        var deleteDbRequest = localDatabase.indexedDB.deleteDatabase(dbName);
        deleteDbRequest.onsuccess = function (event) {
            // database deleted successfully
        };
        deleteDbRequest.onerror = function (e) {
            console.log("Database error: " + e.target.errorCode);
        };
    }
    DBHelper.OpenDatabase=    function () {
        var openRequest = localDatabase.indexedDB.open("dbName");
        openRequest.onerror = function (e) {
            console.log("Database error: " + e.target.errorCode);
        };
        openRequest.onsuccess = function (event) {

            localDatabase.db = openRequest.result;
        };
    }
    DBHelper.CreateObjectStore = function (dbName,objectName,keyPath) {
        var openRequest = localDatabase.indexedDB.open(dbName, 2);
        openRequest.onerror = function (e) {
            console.log("Database error: " + e.target.errorCode);
        };
        openRequest.onsuccess = function (event) {
            localDatabase.db = openRequest.result;
        };
        openRequest.onupgradeneeded = function (evt) {
            var employeeStore = evt.currentTarget.result.createObjectStore
               (objectName, { keyPath: keyPath });
        };
    }
    DBHelper.CreateIndex = function (dbName,objectName,indexArray) {
        var openRequest = localDatabase.indexedDB.open(dbName, 2);
        openRequest.onerror = function (e) {
            console.log("Database error: " + e.target.errorCode);
        };
        openRequest.onsuccess = function (event) {
            db = openRequest.result;
        };
        openRequest.onupgradeneeded = function (evt) {
            if (indexArray && indexArray.length > 0) {
                var objectStore = evt.currentTarget.result.objectStore(objectName);
                for (var i = 0; i < indexArray.length; i++) {
                    var theIndexItem = indexArray[i];
                    //employeeStore.createIndex("stateIndex", "state", { unique: false });
                    objectStore.createIndex(theIndexItem.name, theIndexItem.field, theIndexItem.options);
                }
            }
        };
    }
}

function ChatHistory() {

    ChatHistory.AddMessageItem = function (chatType, messageItem) {

    }

}