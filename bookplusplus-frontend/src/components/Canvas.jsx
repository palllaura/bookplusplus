import React, {forwardRef, useEffect, useImperativeHandle, useState} from 'react';
import {Stage, Layer, Rect, Text, Group, Image as KonvaImage} from 'react-konva';
import {createBook} from "../utils/createBook.js";
import box from "../assets/box.png";
import useImage from 'use-image';

const Canvas = forwardRef(({books, onEdit}, ref) => {
    const [drawnBooks, setDrawnBooks] = useState([]);
    const mm = window.innerHeight / 1000;
    const [boxImage] = useImage(box);
    const cupboardX = 100;
    const cupboardY = 10;
    const cupboardHeight = 960;
    const cupboardWidth = 800;
    const shelfHeight = 280;
    const shelfThickness = 20;
    const shelfWidth = cupboardWidth - 2 * shelfThickness;
    const cupboardColor = '#88664B';
    const shelfColor = '#604734';

    const shelves = [
        {
            id: "shelf1",
            x: (cupboardX + shelfThickness) * mm,
            y: (cupboardY + shelfThickness) * mm,
            width: shelfWidth * mm,
            height: shelfHeight * mm,
            bottom: (cupboardY + shelfThickness + shelfHeight) * mm
        },
        {
            id: "shelf2",
            x: (cupboardX + shelfThickness) * mm,
            y: (cupboardY + shelfHeight + shelfThickness * 2) * mm,
            width: shelfWidth * mm,
            height: shelfHeight * mm,
            bottom: (cupboardY + shelfHeight * 2 + shelfThickness * 2) * mm
        },
        {
            id: "shelf3",
            x: (cupboardX + shelfThickness) * mm,
            y: (cupboardY + shelfHeight * 2 + shelfThickness * 3) * mm,
            width: shelfWidth * mm,
            height: shelfHeight * mm,
            bottom: (cupboardY + shelfHeight * 3 + shelfThickness * 3) * mm
        }
    ];


    useImperativeHandle(ref, () => ({
        getCurrentBookLocations: () => {
            return drawnBooks.map(book => ({
                id: book.id,
                xposition: book.x / mm,
                yposition: book.y / mm
            }));
        },
        reloadBooks: (newBooks) => {
            setDrawnBooks(newBooks.map(book => createBook(book, mm)));
        }
    }));

    useEffect(() => {
        setDrawnBooks(books.map(book => createBook(book, mm)));
    }, [books]);

    const handleDragStart = (bookId, e) => {
        const book = drawnBooks.find(b => b.id === bookId);
        book.prevX = book.x;
        book.prevY = book.y;

        e.target.moveToTop();
        e.target.getLayer().batchDraw();
    };

    function isColliding(a, b) {
        return !(
            a.x + a.width <= b.x ||
            a.x >= b.x + b.width ||
            a.y + a.height <= b.y ||
            a.y >= b.y + b.height
        );
    }

    function isInsideShelf(book, shelf) {
        return (
            book.x >= shelf.x &&
            book.y >= shelf.y &&
            book.x + book.width <= shelf.x + shelf.width &&
            book.y + book.height <= shelf.y + shelf.height
        );
    }


    const handleDragMove = (e, bookId) => {
        const movingBook = drawnBooks.find(b => b.id === bookId);
        const updatedBook = {
            ...movingBook,
            x: e.target.x(),
            y: e.target.y(),
        };

        const collides = drawnBooks.some(
            b => b.id !== bookId && isColliding(updatedBook, b)
        );
        const insideAnyShelf = shelves.some(shelf => isInsideShelf(updatedBook, shelf));
        const incorrectPlacement = collides || !insideAnyShelf;

        setDrawnBooks(drawnBooks.map(b =>
            b.id === bookId ? { ...updatedBook, isColliding: incorrectPlacement } : b
        ));
    };

    const handleDragEnd = (e, bookId) => {
        const book = drawnBooks.find(b => b.id === bookId);
        let newX = e.target.x();
        let newY = e.target.y();

        const updatedBook = {
            ...book,
            x: newX,
            y: newY,
            prevX: book.prevX,
            prevY: book.prevY,
        };

        const collidesWithBook = drawnBooks.some(
            b => b.id !== bookId && isColliding(updatedBook, b)
        );

        const insideShelf = shelves.find(shelf => isInsideShelf(updatedBook, shelf));

        if (collidesWithBook || !insideShelf) {
            e.target.position({ x: book.prevX, y: book.prevY });
            updatedBook.x = book.prevX;
            updatedBook.y = book.prevY;
        } else {
            updatedBook.y = insideShelf.bottom - book.height;
            e.target.position({ x: newX, y: updatedBook.y });

            updatedBook.prevX = updatedBook.x;
            updatedBook.prevY = updatedBook.y;
        }

        updatedBook.isColliding = false;
        setDrawnBooks(drawnBooks.map(b => b.id === bookId ? updatedBook : b));
    };

    return (
        <Stage
            width={window.innerWidth}
            height={window.innerHeight}
            style={{backgroundColor: '#F7E1CA'}}
        >
            <Layer>
                <Rect
                    x={cupboardX * mm}
                    y={cupboardY * mm}
                    height={cupboardHeight * mm}
                    width={cupboardWidth * mm}
                    fill={cupboardColor}
                />
            </Layer>
            <Layer>
                {shelves.map(shelf => (
                    <Rect
                        key={shelf.id}
                        x={shelf.x}
                        y={shelf.y}
                        width={shelf.width}
                        height={shelf.height}
                        fill={shelfColor}
                    />
                ))}
            </Layer>
            <Layer>
                {drawnBooks.map(book => (
                    <Group
                        key={book.id}
                        x={book.x}
                        y={book.y}
                        draggable
                        onDragStart={(e) => handleDragStart(book.id, e)}
                        onDragMove={(e) => handleDragMove(e, book.id)}
                        onDragEnd={(e) => handleDragEnd(e, book.id)}
                        onDblClick={() => onEdit(book.id)}
                    >
                        <Rect
                            width={book.width}
                            height={book.height}
                            fill={book.color}
                            cornerRadius={2}
                        />
                        {book.isColliding && (
                            <Rect
                                width={book.width}
                                height={book.height}
                                fill={'red'}
                                opacity={0.8}
                                cornerRadius={2}
                            />
                        )}
                        <Text
                            x={0}
                            y={book.height - 5*mm}
                            text={book.title}
                            fill={book.fontcolor}
                            fontSize={book.fontsize}
                            rotation={-90}
                            width={book.height - 10*mm}
                            height={book.width}
                            verticalAlign={'middle'}
                        />
                    </Group>
                ))}
            </Layer>

            <Layer>
                {boxImage && (
                    <KonvaImage
                        image={boxImage}
                        x={window.innerWidth - 500*mm}
                        y={window.innerHeight - 250*mm}
                        width={200*mm}
                        height={200*mm}
                        draggable
                    />
                )}
            </Layer>
        </Stage>
    );
});

export default Canvas;
