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