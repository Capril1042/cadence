export interface AnalyticsEventRequest {
    eventName: string
    memberId?: number
    classSessionId?: number
}

export async function trackEvent(
    event: AnalyticsEventRequest
): Promise<void> {
    const params = new URLSearchParams()

    params.append('eventName', event.eventName)

    if (event.memberId !== undefined) {
        params.append('memberId', event.memberId.toString())
    }

    if (event.classSessionId !== undefined) {
        params.append('classSessionId', event.classSessionId.toString())
    }

    const response = await fetch(
        `http://localhost:8080/api/analytics/events?${params.toString()}`,
        {
            method: 'POST',
        }
    )

    if (!response.ok) {
        throw new Error('Failed to record analytics event')
    }
}

export interface BookingFunnel {
    classViews: number
    bookingStarts: number
    bookingCompletions: number
    viewToStartRate: number
    startToCompleteRate: number
    overallConversionRate: number
}

export interface ClassFunnel extends BookingFunnel {
    classSessionId: number
}

export interface ClassAnalyticsSummary {
    classSessionId: number
    className: string
    views: number
    bookingStarts: number
    bookingsCompleted: number
    viewToStartRate: number
    viewToBookingRate: number
    bookingAbandonmentRate: number | null
}

export async function getBookingFunnel(): Promise<BookingFunnel> {
    const response = await fetch(
        'http://localhost:8080/api/analytics/events/funnel'
    )

    if (!response.ok) {
        throw new Error('Failed to fetch booking funnel')
    }

    return response.json()
}

export async function getClassFunnel(
    classSessionId: number
): Promise<ClassFunnel> {
    const response = await fetch(
        `http://localhost:8080/api/analytics/events/funnel/${classSessionId}`
    )

    if (!response.ok) {
        throw new Error('Failed to fetch class funnel')
    }

    return response.json()
}

export async function getClassAnalyticsSummary(): Promise<ClassAnalyticsSummary[]> {
    const response = await fetch(
        'http://localhost:8080/api/analytics/events/class-summary'
    )

    if (!response.ok) {
        throw new Error('Failed to fetch class analytics summary')
    }

    const rows: (string | number | null)[][] = await response.json()

    return rows.map((row) => ({
        classSessionId: Number(row[0]),
        className: String(row[1]),
        views: Number(row[2]),
        bookingStarts: Number(row[3]),
        bookingsCompleted: Number(row[4]),
        viewToStartRate: Number(row[5]),
        viewToBookingRate: Number(row[6]),
        bookingAbandonmentRate:
            row[7] === null ? null : Number(row[7]),
    }))
}