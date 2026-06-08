package com.easetravel.notification.util;

public class EmailTemplate {

    public static String buildBookingConfirmationHtml(Long bookingId, String tripName, String guestName) {
        return """
                <html><body style="font-family:Arial,sans-serif;background:#f5f5f5;padding:20px">
                <div style="max-width:600px;margin:auto;background:white;border-radius:8px;padding:30px;box-shadow:0 2px 8px rgba(0,0,0,0.1)">
                  <h2 style="color:#2c7be5">🎉 Booking Confirmed!</h2>
                  <p>Dear <strong>%s</strong>,</p>
                  <p>Your booking has been successfully created.</p>
                  <table style="width:100%%;border-collapse:collapse;margin:20px 0">
                    <tr><td style="padding:8px;background:#f8f9fa;font-weight:bold">Booking ID</td><td style="padding:8px">%d</td></tr>
                    <tr><td style="padding:8px;font-weight:bold">Trip</td><td style="padding:8px">%s</td></tr>
                  </table>
                  <p style="color:#28a745">✅ Your booking is pending payment confirmation.</p>
                  <p>Thank you for choosing <strong>EaseTravel</strong>!</p>
                </div></body></html>
                """.formatted(guestName, bookingId, tripName);
    }

    public static String buildPaymentSuccessHtml(String guestName, String transactionId, String amount) {
        return """
                <html><body style="font-family:Arial,sans-serif;background:#f5f5f5;padding:20px">
                <div style="max-width:600px;margin:auto;background:white;border-radius:8px;padding:30px">
                  <h2 style="color:#28a745">✅ Payment Successful!</h2>
                  <p>Dear <strong>%s</strong>,</p>
                  <p>Your payment has been processed successfully.</p>
                  <table style="width:100%%;border-collapse:collapse;margin:20px 0">
                    <tr><td style="padding:8px;background:#f8f9fa;font-weight:bold">Transaction ID</td><td style="padding:8px">%s</td></tr>
                    <tr><td style="padding:8px;font-weight:bold">Amount Paid</td><td style="padding:8px">₹%s</td></tr>
                  </table>
                  <p>Your booking is now <strong>CONFIRMED</strong>. Have a great trip!</p>
                  <p>Thank you for choosing <strong>EaseTravel</strong>!</p>
                </div></body></html>
                """.formatted(guestName, transactionId, amount);
    }

    public static String buildPaymentFailedHtml(String guestName, String reason) {
        return """
                <html><body style="font-family:Arial,sans-serif;background:#f5f5f5;padding:20px">
                <div style="max-width:600px;margin:auto;background:white;border-radius:8px;padding:30px">
                  <h2 style="color:#dc3545">❌ Payment Failed</h2>
                  <p>Dear <strong>%s</strong>,</p>
                  <p>Unfortunately, your payment could not be processed.</p>
                  <p><strong>Reason:</strong> %s</p>
                  <p>Please try again or contact support at support@easetravel.com</p>
                  <p>Thank you for choosing <strong>EaseTravel</strong>!</p>
                </div></body></html>
                """.formatted(guestName, reason);
    }

    public static String buildCancellationHtml(String guestName, Long bookingId) {
        return """
                <html><body style="font-family:Arial,sans-serif;background:#f5f5f5;padding:20px">
                <div style="max-width:600px;margin:auto;background:white;border-radius:8px;padding:30px">
                  <h2 style="color:#fd7e14">🚫 Booking Cancelled</h2>
                  <p>Dear <strong>%s</strong>,</p>
                  <p>Your booking <strong>#%d</strong> has been cancelled.</p>
                  <p>If you did not request this, please contact us immediately.</p>
                  <p>Thank you for choosing <strong>EaseTravel</strong>!</p>
                </div></body></html>
                """.formatted(guestName, bookingId);
    }
}

