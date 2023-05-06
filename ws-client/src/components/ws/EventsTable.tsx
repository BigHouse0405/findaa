import * as React from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import AuthenticationFailureEvent from "../AuthenticationFailureEvent";

interface Column {
    id: 'content' | 'timestamp';
    label: string;
    minWidth?: number;
    align?: 'right' | 'left' | 'center';
    format?: (value: Date) => string;
}

const columns: readonly Column[] = [
    {
        id: 'content',
        label: 'Event',
        minWidth: 170,
        align: 'center',
    },
    {
        id: 'timestamp',
        label: 'Timestamp',
        minWidth: 170,
        align: 'center',
        format: (value: Date) => {
            return value.getDay().valueOf() + " "
                + value.getMonth().valueOf()  + " "
                + value.getFullYear().valueOf()  + " "
                + value.getHours().valueOf()  + ":"
                + value.getMinutes().valueOf()
                + ":" + value.getSeconds().valueOf()
        },
    },
];


type Props = {
    events: AuthenticationFailureEvent[];
};


const EventsTable: React.FC<Props> = ({events}) => {
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    // const sortedEvents = [...events].sort(
    //     (a, b) => b.timestamp - a.timestamp
    // );


    return (
        <Paper sx={{ width: '50%', overflow: 'hidden', position: 'absolute', marginTop: "25%"}}>
            <TableContainer sx={{ maxHeight: 440 }}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead>
                        <TableRow>
                            {columns.map((column) => (
                                <TableCell
                                    key={column.id}
                                    align={column.align}
                                    style={{ minWidth: column.minWidth, backgroundColor: 'rgba(249, 246, 237, 1)' }}
                                >
                                    {column.label}
                                </TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {events
                            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                            .map((event) => {
                                return (
                                    <TableRow hover role="checkbox" tabIndex={-1} key={event.timestamp}>
                                        {columns.map((column) => {
                                            const value = event[column.id];
                                            return (
                                                <TableCell key={column.id} align={column.align}>
                                                    {column.format && typeof value === 'number'
                                                        ? column.format(value)
                                                        : value}
                                                </TableCell>
                                            );
                                        })}
                                    </TableRow>
                                );
                            })}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                component="div"
                count={events.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Paper>
    );
}

export default EventsTable;
