import { useEffect, useState } from 'react'
import type { ClassSession } from '../../types/ClassSession'
import type { Booking } from '../../types/Booking'
import {
    bookClass,
    getUpcomingClasses,
    getBookings,
} from '../../services/classService'

function UpcomingClasses() {
    const [classes, setClasses] = useState<ClassSession[]>([])
    const [filter, setFilter] = useState<'all' | 'available' | 'full'>('all')
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)
    const [bookingId, setBookingId] = useState<number | null>(null)
    const [bookingMessage, setBookingMessage] = useState<string | null>(null)
    const [bookingMessageClassId, setBookingMessageClassId] = useState<number | null>(null)
    const [bookingMessageType, setBookingMessageType] = useState<'success' | 'error' | null>(null)
    const [bookings, setBookings] = useState<Booking[]>([])

    useEffect(() => {
        async function loadClasses() {
            try {
                const classData = await getUpcomingClasses()
                const bookingData = await getBookings()

                setClasses(classData)
                setBookings(bookingData)
            } catch {
                setError('Unable to load classes. Please try again.')
            } finally {
                setLoading(false)
            }
        }

        loadClasses()
    }, [])
    async function handleBooking(classId: number) {
        setBookingId(classId)
        setBookingMessage(null)
        setBookingMessageClassId(classId)

        try {
            const newBooking = await bookClass(1, classId)

            setBookings((currentBookings) => [
                ...currentBookings,
                newBooking,
            ])

            setBookingMessage(
                `${newBooking.classSession.name} successfully booked!`
            )
            setBookingMessageType('success')
        } catch (error) {
            setBookingMessage(
                error instanceof Error
                    ? error.message
                    : 'Unable to book this class. Please try again.'
            )
            setBookingMessageType('error')
        } finally {
            setBookingId(null)
        }
    }

    function getBookingCount(classId: number): number {
        return bookings.filter(
            (booking) =>
                booking.classSession.id === classId &&
                booking.status === 'BOOKED'
        ).length
    }

    const filteredClasses = classes.filter((classSession) => {
        const bookingCount = getBookingCount(classSession.id)

        if (filter === 'available') {
            return bookingCount < classSession.capacity
        }

        if (filter === 'full') {
            return bookingCount >= classSession.capacity
        }

        return true
    })
    return (
        <section className="mt-10">
            <div className="mb-5 flex items-center justify-between">
                <div>
                    <h3 className="text-xl font-bold text-slate-900">
                        Upcoming Classes
                    </h3>
                    <p className="mt-1 text-sm text-slate-500">
                        Here's what's on the schedule.
                    </p>
                </div>

                <button className="rounded-lg bg-indigo-600 px-4 py-2 text-sm font-medium text-white hover:bg-indigo-700">
                    View Schedule
                </button>
            </div>

            <div className="mb-4 flex gap-2">
                <button
                    onClick={() => setFilter('all')}
                    className={`rounded-lg px-4 py-2 text-sm font-medium ${
                        filter === 'all'
                            ? 'bg-indigo-600 text-white'
                            : 'bg-white text-slate-600 hover:bg-slate-100'
                    }`}
                >
                    All Classes
                </button>

                <button
                    onClick={() => setFilter('available')}
                    className={`rounded-lg px-4 py-2 text-sm font-medium ${
                        filter === 'available'
                            ? 'bg-indigo-600 text-white'
                            : 'bg-white text-slate-600 hover:bg-slate-100'
                    }`}
                >
                    Available
                </button>

                <button
                    onClick={() => setFilter('full')}
                    className={`rounded-lg px-4 py-2 text-sm font-medium ${
                        filter === 'full'
                            ? 'bg-indigo-600 text-white'
                            : 'bg-white text-slate-600 hover:bg-slate-100'
                    }`}
                >
                    Fully Booked
                </button>
            </div>

            {loading ? (
                <p className="py-10 text-center text-slate-500">
                    Loading classes...
                </p>
            ) : error ? (
                <p className="py-10 text-center text-red-600">
                    {error}
                </p>
            ) : (
                <div className="overflow-hidden rounded-xl border border-slate-200 bg-white">
                    <div className="grid grid-cols-4 gap-4 border-b border-slate-200 px-6 py-4 text-sm font-semibold text-slate-500">
                        <span>Class</span>
                        <span>Instructor</span>
                        <span>Time</span>
                        <span>Bookings</span>
                    </div>

                    {filteredClasses.length > 0 ? (
                        filteredClasses.map((classSession) => (
                            <div
                                key={classSession.id}
                                className="grid grid-cols-4 gap-4 border-t border-slate-100 px-6 py-5 text-sm"
                            >
                    <span className="font-medium text-slate-900">
                        {classSession.name}
                    </span>

                                <span className="text-slate-600">
                        {classSession.instructor}
                    </span>

                                <span className="text-slate-600">
                        {classSession.time}
                    </span>

                                <div>
                                    {getBookingCount(classSession.id)} / {classSession.capacity}

                                    <span className={`text-xs font-medium ${
                                        getBookingCount(classSession.id) >= classSession.capacity
                                            ? 'text-red-600'
                                            : 'text-slate-400'
                                    }`}>
        {getBookingCount(classSession.id) >= classSession.capacity
            ? ' Fully booked'
            : ` ${classSession.capacity - getBookingCount(classSession.id)} spots left`}
    </span>

                                    <div className="mt-2 h-2 w-full rounded-full bg-slate-100">
                                        <div
                                            className="h-2 rounded-full bg-indigo-600"
                                            style={{
                                                width: `${(getBookingCount(classSession.id) / classSession.capacity) * 100}%`,
                                            }}
                                        />
                                    </div>
                                </div>
                                {getBookingCount(classSession.id) < classSession.capacity && (
                                    <button
                                        onClick={() => handleBooking(classSession.id)}
                                        disabled={bookingId === classSession.id}
                                        className="mt-3 rounded-lg bg-indigo-600 px-3 py-2 text-xs font-medium text-white hover:bg-indigo-700 disabled:opacity-50"
                                    >
                                        {bookingId === classSession.id ? 'Booking...' : 'Book Class'}
                                    </button>
                                )}
                                {bookingMessage && bookingMessageClassId === classSession.id && (
                                    <p
                                        className={`mb-4 text-sm font-medium ${
                                            bookingMessageType === 'success'
                                                ? 'text-green-600'
                                                : 'text-red-600'
                                        }`}
                                    >
                                        {bookingMessage}
                                    </p>
                                )}
                            </div>
                        ))
                    ) : (
                        <div className="px-6 py-10 text-center text-sm text-slate-500">
                            No classes match this filter.
                        </div>
                    )}
                </div>
            )}
        </section>
    )
}

export default UpcomingClasses