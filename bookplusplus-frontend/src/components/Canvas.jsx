import '../App.css';
import {useEffect, useRef} from 'react';

const Canvas = () => {

    function createBook({width = 50, height = 120, color = '#D9722C', x = 100, y = 100, title = 'Untitled'}) {
        return {width, height, color, x, y, title};
    }

    const book1 = createBook({x: 100, y: 100, title: 'Book 1'});
    const book2 = createBook({x: 160, y: 100, title: 'Book 2', color: '#E84636'});
    const book3 = createBook({width: 80, height: 140, color: '#FEB72BFF', x: 220, y: 80, title: 'Book 3'});

    const books = [book1, book2, book3];

    const canvasRef = useRef(null);

    const draw = (ctx, width, height) => {
        ctx.clearRect(0, 0, width, height);

        books.forEach(book => {
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

        resizeCanvas();

        window.addEventListener('resize', resizeCanvas);
        return () => window.removeEventListener('resize', resizeCanvas);
    }, []);

    return <canvas className="canvas" ref={canvasRef}/>;
};

export default Canvas;
