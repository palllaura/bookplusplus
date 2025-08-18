export async function fetchBooks() {
    const response = await fetch('http://localhost:8080/api/allBooks');
    return await response.json();
}

export async function saveBookLocations(locations) {
    const response = await fetch('http://localhost:8080/api/saveLocations', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(locations),
    });

    if (!response.ok) {
        throw new Error('Failed to save book locations');
    }
}

export async function addBook(dto) {
    const response = await fetch('http://localhost:8080/api/addBook', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            title: dto.title,
            pages: dto.pages,
            height: dto.height,
            color: dto.color
        }),
    });

    if (!response.ok) {
        throw new Error('Failed to save book');
    }
}
