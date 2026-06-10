## Media Vault

Media Vault is a local application for managing personal media collections, including books, movies, and series.

### Overview

The application uses a local MySQL database for data storage. Only essential connection details are required, such as username, password, and database URL. These credentials are configured in the `hibernate.cfg.xml` file to enable a Java-based connection to the database via Hibernate.

### Features

Users can choose between two main functions:

- Adding entries to the Vault  
- Viewing all stored entries in a complete list  

The application supports filtering entries by:

- ID  
- Title  
- Genre  
- Media type  

Media types are implemented as classes derived from a base `Media` class. Additional custom types can be created, such as `Album` or `Vinyl`.

### Entry Management

Entries can be expanded to reveal detailed options, allowing users to:

- Delete entries  
- Edit entries  

To edit an entry, modify the desired fields in the provided form and confirm the changes by clicking **"Change"** in the corresponding window.

After making changes, it is recommended to click **"Reload"** to refresh the list and display the updated data.

### Known Issues

There are still a few known issues in the current implementation, including:

- Slow loading performance in certain cases  
- ~Occasional persistence of outdated rating values after editing entries~

These issues are currently under investigation and will be addressed in future updates.
