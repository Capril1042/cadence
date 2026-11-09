import { useEffect, useState } from 'react'
import {
    getOverallRetention,
    getClassRetention,
    getInactiveMembers,
    getNeverBookedMembers,
    getCancelledOnlyMembers,
    type OverallRetention,
    type ClassRetention,
    type MemberRetention,
} from '../../services/retentionService'

function RetentionOverview() {
    const [retention, setRetention] = useState<OverallRetention | null>(null)
    const [classRetention, setClassRetention] = useState<ClassRetention[]>([])
    const [inactiveMembers, setInactiveMembers] = useState<MemberRetention[]>([])
    const [neverBookedMembers, setNeverBookedMembers] = useState<MemberRetention[]>([])
    const [error, setError] = useState<string | null>(null)
    const [cancelledOnlyMembers, setCancelledOnlyMembers] = useState<MemberRetention[]>([])

    useEffect(() => {
        async function loadRetention() {
            try {
                const [overall, classes, inactive, neverBooked, cancelledOnly] = await Promise.all([
                    getOverallRetention(),
                    getClassRetention(),
                    getInactiveMembers(),
                    getNeverBookedMembers(),
                    getCancelledOnlyMembers()
                ])

                setRetention(overall)
                setClassRetention(classes)
                setInactiveMembers(inactive)
                setNeverBookedMembers(neverBooked)
                setCancelledOnlyMembers(cancelledOnly)
            } catch {
                setError('Unable to load retention insights.')
            }
        }

        loadRetention()
    }, [])

    if (error) {
        return (
            <div className="mt-8 rounded-xl bg-white p-6 shadow-sm">
                <p className="text-sm text-red-600">{error}</p>
            </div>
        )
    }

    if (!retention) {
        return (
            <div className="mt-8 rounded-xl bg-white p-6 shadow-sm">
                <p className="text-sm text-slate-500">
                    Loading retention insights...
                </p>
            </div>
        )
    }

    return (
        <section className="mt-8">
            <div className="mb-5">
                <h2 className="text-xl font-bold text-slate-900">
                    Member Retention
                </h2>
                <p className="mt-1 text-sm text-slate-500">
                    Understand how often members return to book classes.
                </p>
            </div>

            <div className="grid grid-cols-1 gap-6 md:grid-cols-3">
                <div className="rounded-xl bg-white p-6 shadow-sm">
                    <p className="text-sm text-slate-500">Repeat Booking Rate</p>
                    <p className="mt-2 text-3xl font-bold text-slate-900">
                        {retention.repeatBookingRate.toFixed(1)}%
                    </p>
                </div>

                <div className="rounded-xl bg-white p-6 shadow-sm">
                    <p className="text-sm text-slate-500">Returning Bookers</p>
                    <p className="mt-2 text-3xl font-bold text-slate-900">
                        {retention.returningBookers}
                    </p>
                </div>

                <div className="rounded-xl bg-white p-6 shadow-sm">
                    <p className="text-sm text-slate-500">One-Time Bookers</p>
                    <p className="mt-2 text-3xl font-bold text-slate-900">
                        {retention.oneTimeBookers}
                    </p>
                </div>

                <div className="rounded-xl bg-white p-6 shadow-sm">
                    <p className="text-sm text-slate-500">Cancelled-Only Members</p>
                    <p className="mt-2 text-3xl font-bold text-slate-900">
                        {cancelledOnlyMembers.length}
                    </p>
                    <p className="mt-1 text-xs text-slate-400">
                        Booking history, but no active bookings
                    </p>
                </div>

                <div className="rounded-xl bg-white p-6 shadow-sm">
                    <p className="text-sm text-slate-500">Inactive Members</p>
                    <p className="mt-2 text-3xl font-bold text-slate-900">
                        {inactiveMembers.length}
                    </p>
                    <p className="mt-1 text-xs text-slate-400">
                        No active booking in over 30 days
                    </p>
                </div>

                <div className="rounded-xl bg-white p-6 shadow-sm">
                    <p className="text-sm text-slate-500">Never Booked</p>
                    <p className="mt-2 text-3xl font-bold text-slate-900">
                        {neverBookedMembers.length}
                    </p>
                    <p className="mt-1 text-xs text-slate-400">
                        No booking history
                    </p>
                </div>
            </div>

            <div className="mt-6 rounded-xl bg-white p-6 shadow-sm">
                <h3 className="text-lg font-semibold text-slate-900">
                    Class Loyalty
                </h3>

                {classRetention.length === 0 ? (
                    <p className="mt-4 text-sm text-slate-500">
                        No repeat class bookings yet.
                    </p>
                ) : (
                    <div className="mt-4 space-y-4">
                        {classRetention.map((item) => (
                            <div
                                key={item.classSessionId}
                                className="flex items-center justify-between border-b border-slate-100 pb-3 last:border-0"
                            >
                                <span className="font-medium text-slate-700">
                                    {item.className}
                                </span>
                                <span className="text-sm text-slate-500">
                                    {item.returningMembers} returning members
                                </span>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </section>
    )
}

export default RetentionOverview