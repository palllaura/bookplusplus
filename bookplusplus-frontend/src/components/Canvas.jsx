import '../App.css';
import { forwardRef, useEffect, useImperativeHandle, useRef } from 'react';
import { createBook } from '../utils/createBook';

const Canvas = forwardRef(({ books }, ref) => {
    const canvasRef = useRef(null);
    const draggingBookRef = useRef(null);
    const offsetRef = useRef({ x: 0, y: 0 });
    const booksRef = useRef([]);

    useImperativeHandle(ref, () => ({
        getCurrentBookLocations: () => {
            return booksRef.current.map(book => ({
                id: book.id,
                xposition: book.x,
                yposition: book.y
            }));
        }
    }));

    useEffect(() => {
        booksRef.current = books.map(book => createBook(book));

        const canvas = canvasRef.current;
        const parent = canvas.parentElement;

        canvas.width = parent.clientWidth;
        canvas.height = parent.clientHeight;

        const ctx = canvas.getContext('2d');
        draw(ctx, canvas.width, canvas.height);
    }, [books]);

    const draw = (ctx, width, height) => {
        ctx.clearRect(0, 0, width, height);

        booksRef.current.forEach(book => {
            ctx.fillStyle = book.color;
            ctx.fillRect(book.x, book.y, book.width, book.height);
            console.log(book.title + ' x:' + book.x + ' y:' + book.y)

            ctx.save();
            ctx.translate(book.x + book.width / 2 + 8, book.y + book.height - 8);
            ctx.rotate(-Math.PI / 2);
            ctx.fillStyle = 'white';
            ctx.font = '16px sans-serif';
            ctx.fillText(book.title, 0, 0);
            ctx.restore();
        })
    };

    useEffect(() => {
        const canvas = canvasRef.current;
        const parent = canvas.parentElement;

        const resizeCanvas = () => {
            canvas.width = parent.clientWidth;
            canvas.height = parent.clientHeight;

            const ctx = canvas.getContext('2d');
            draw(ctx, canvas.width, canvas.height);
        };

        const handleMouseDown = (e) => {
            const rect = canvas.getBoundingClientRect();
            const mouseX = e.clientX - rect.left;
            const mouseY = e.clientY - rect.top;

            for (const book of booksRef.current) {
                if (
                    mouseX >= book.x &&
                    mouseX <= book.x + book.width &&
                    mouseY >= book.y &&
                    mouseY <= book.y + book.height
                ) {
                    draggingBookRef.current = book;
                    offsetRef.current = {
                        x: mouseX - book.x,
                        y: mouseY - book.y,
                    };
                    break;
                }
            }
        };

        const handleMouseMove = (e) => {
            if (!draggingBookRef.current) return;

            const rect = canvas.getBoundingClientRect();
            const mouseX = e.clientX - rect.left;
            const mouseY = e.clientY - rect.top;

            draggingBookRef.current.x = mouseX - offsetRef.current.x;
            draggingBookRef.current.y = mouseY - offsetRef.current.y;

            const ctx = canvas.getContext('2d');
            draw(ctx, canvas.width, canvas.height);
        };

        const handleMouseUp = () => {
            draggingBookRef.current = null;
        };

        resizeCanvas();

        canvas.addEventListener('mousedown', handleMouseDown);
        canvas.addEventListener('mousemove', handleMouseMove);
        canvas.addEventListener('mouseup', handleMouseUp);

        window.addEventListener('resize', resizeCanvas);
        return () => {
            window.removeEventListener('resize', resizeCanvas);
            canvas.removeEventListener('mousedown', handleMouseDown);
            canvas.removeEventListener('mousemove', handleMouseMove);
            canvas.removeEventListener('mouseup', handleMouseUp);
        };
    }, []);

    return <canvas className="canvas" ref={canvasRef}/>;
});

export default Canvas;
