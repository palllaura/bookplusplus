import '../App.css';
import {useEffect, useRef} from 'react';

const Canvas = () => {
    const draggingBookRef = useRef(null);
    const offsetRef = useRef({ x: 0, y: 0 });

    function createBook({width = 50, height = 120, color = '#D9722C', x = 100, y = 100, title = 'Untitled'}) {
        return {width, height, color, x, y, title};
    }

    const booksRef = useRef([
        createBook({ x: 100, y: 100, title: 'Book 1' }),
        createBook({ x: 160, y: 100, title: 'Book 2', color: '#E84636' }),
        createBook({ width: 80, height: 140, color: '#FEB72BFF', x: 220, y: 80, title: 'Book 3' }),
    ]);

    const canvasRef = useRef(null);

    const draw = (ctx, width, height) => {
        ctx.clearRect(0, 0, width, height);

        booksRef.current.forEach(book => {
            ctx.fillStyle = book.color;
            ctx.fillRect(book.x, book.y, book.width, book.height);

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
};

export default Canvas;
