package org.example.authservice.util;


import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor

public class EmailTemplates {

    private String VerificationTemplate;
    private String ForgotPasswordTemplate;

    @PostConstruct
    public void init (){
        this.VerificationTemplate = """
                <!DOCTYPE html>
                <html
                        lang="en"
                >
                <head>
                    <meta charset="utf-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                    <meta name="color-scheme" content="normal" />
                    <meta name="supported-color-schemes" content="normal" />
                    <title>Verify your email - Offoura</title>
                    <style>
                        @import url("https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap");
                
                        :root {
                            color-scheme: normal;
                            supported-color-schemes: normal;
                        }
                
                        body {
                            margin: 0;
                            padding: 0;
                            background-color: #1a1a1a !important;
                            background-image: linear-gradient(
                                    to bottom,
                                    #1a1a1a,
                                    #2d2d2d
                            ) !important;
                            font-family: "Inter", Arial, sans-serif;
                            font-size: 15px;
                            line-height: 1.5;
                            color: #222222;
                            -webkit-text-size-adjust: 100%;
                            -ms-text-size-adjust: 100%;
                        }
                
                        /* Force consistent colors regardless of mode */
                        .force-normal {
                            background-color: #ffffff !important;
                            color: #222222 !important;
                        }
                
                        .dark-header {
                            background-color: #000000 !important;
                            color: #ffffff !important;
                        }
                
                        .dark-button {
                            background-color: #000000 !important;
                            color: #ffffff !important;
                            border: 1px solid #000000 !important;
                        }
                
                        .gray-box {
                            background-color: #f9f9f9 !important;
                            border-left: 3px solid #000000 !important;
                            color: #333333 !important;
                        }
                
                        p {
                            margin: 0 0 16px 0;
                        }
                
                        h1 {
                            margin: 0 0 10px 0;
                            font-size: 22px;
                            line-height: 1.3;
                            font-weight: 600;
                            color: #222222 !important;
                        }
                
                        /* Override for dark mode in various clients */
                        @media (prefers-color-scheme: dark) {
                            .force-normal {
                                background-color: #ffffff !important;
                                color: #222222 !important;
                            }
                
                            h1,
                            .dark-text {
                                color: #222222 !important;
                            }
                
                            p,
                            .normal-text {
                                color: #333333 !important;
                            }
                
                            .dark-header {
                                background-color: #000000 !important;
                                color: #ffffff !important;
                            }
                
                            .dark-button {
                                background-color: #000000 !important;
                                color: #ffffff !important;
                            }
                
                            .gray-box {
                                background-color: #f9f9f9 !important;
                                color: #333333 !important;
                            }
                
                            * [data-ogsc] .force-normal {
                                background-color: #ffffff !important;
                                color: #222222 !important;
                            }
                        }
                    </style>
                </head>
                <body
                        style="
                      margin: 0;
                      padding: 0;
                      background-color: #f7f7f7 !important;
                      font-family: 'Inter', Arial, sans-serif;
                      font-size: 15px;
                      line-height: 1.5;
                      color: #222222;
                    "
                >
                <!-- Hidden preheader text -->
                <div
                        style="
                        display: none;
                        font-size: 1px;
                        line-height: 1px;
                        max-height: 0px;
                        max-width: 0px;
                        opacity: 0;
                        overflow: hidden;
                        mso-hide: all;
                        font-family: sans-serif;
                      "
                >
                    Verify your Offoura email address to complete registration...
                </div>
                
                <table
                        role="presentation"
                        cellpadding="0"
                        cellspacing="0"
                        border="0"
                        width="100%"
                        style="background-color: #f7f7f7 !important; padding: 30px 0"
                >
                    <tr>
                        <td align="center" valign="top">
                            <table
                                    class="force-normal"
                                    cellpadding="0"
                                    cellspacing="0"
                                    border="0"
                                    width="580"
                                    style="
                              margin: 0 auto;
                              width: 100%;
                              max-width: 580px;
                              background-color: #ffffff !important;
                              border-radius: 12px;
                              box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
                            "
                            >
                                <tr>
                                    <td
                                            class="force-normal"
                                            style="
                                  background-color: #ffffff !important;
                                  border-radius: 12px;
                                  overflow: hidden;
                                  color: #222222 !important;
                                "
                                    >
                                        <!-- Header with Logo -->
                                        <table cellpadding="0" cellspacing="0" border="0" width="100%">
                                            <tr>
                                                <td
                                                        align="center"
                                                        class="dark-header"
                                                        style="
                                        padding: 30px 0;
                                        border-radius: 12px 12px 0 0;
                                        background-color: #000000 !important;
                                        background-image: linear-gradient(
                                          to right,
                                          #000000,
                                          #000000
                                        ) !important;
                                      "
                                                >
                                                    <img
                                                            src="http://cdn.mcauto-images-production.sendgrid.net/62a8130ba3eae887/5aff02af-46fe-4e3b-8753-60c39c89498c/5756x2157.png"
                                                            width="180"
                                                            alt="Offoura Logo"
                                                            style="max-width: 180px; height: auto; border: 0"
                                                    />
                                                </td>
                                            </tr>
                                        </table>
                
                                        <!-- Main Content -->
                                        <table
                                                class="force-normal"
                                                cellpadding="0"
                                                cellspacing="0"
                                                border="0"
                                                width="100%"
                                                style="background-color: #ffffff !important"
                                        >
                                            <tr>
                                                <td
                                                        align="left"
                                                        class="force-normal"
                                                        style="
                                        padding: 40px 40px 20px 40px;
                                        background-color: #ffffff !important;
                                        color: #222222 !important;
                                      "
                                                >
                                                    <h1
                                                            style="
                                          margin: 0 0 10px 0;
                                          font-size: 22px;
                                          line-height: 1.3;
                                          font-weight: 600;
                                          color: #222222 !important;
                                          font-family: 'Inter', Arial, sans-serif;
                                          background-color: #ffffff !important;
                                        "
                                                    >
                                                        Hello {{User}},
                                                    </h1>
                
                                                    <p
                                                            class="normal-text"
                                                            style="
                                          margin: 0 0 25px 0;
                                          font-family: 'Inter', Arial, sans-serif;
                                          font-size: 15px;
                                          line-height: 1.5;
                                          color: #333333 !important;
                                          background-color: #ffffff !important;
                                        "
                                                    >
                                                        Thank you for joining Offoura! Please verify your email
                                                        address to complete your registration and start
                                                        showcasing your offers to potential customers.
                                                    </p>
                
                                                    <!-- Button -->
                                                    <table
                                                            class="force-normal"
                                                            cellspacing="0"
                                                            cellpadding="0"
                                                            border="0"
                                                            width="100%"
                                                            style="
                                          min-width: 100%;
                                          background-color: #ffffff !important;
                                        "
                                                    >
                                                        <tr>
                                                            <td
                                                                    align="center"
                                                                    class="force-normal"
                                                                    style="
                                              padding: 5px 0 25px 0;
                                              background-color: #ffffff !important;
                                            "
                                                            >
                                                                <table
                                                                        cellspacing="0"
                                                                        cellpadding="0"
                                                                        border="0"
                                                                        style="width: 240px; min-width: 240px"
                                                                >
                                                                    <tr>
                                                                        <td
                                                                                align="center"
                                                                                class="dark-button"
                                                                                style="
                                                    border-radius: 8px;
                                                    background-color: #000000 !important;
                                                    background-image: linear-gradient(
                                                      to right,
                                                      #000000,
                                                      #000000
                                                    ) !important;
                                                  "
                                                                        >
                                                                            <a
                                                                                    href="{{redirecturl}}"
                                                                                    target="_blank"
                                                                                    class="dark-button"
                                                                                    style="
                                                      display: inline-block;
                                                      min-width: 240px;
                                                      padding: 15px 0;
                                                      font-family: 'Inter', Arial, sans-serif;
                                                      font-size: 16px;
                                                      color: #ffffff !important;
                                                      text-decoration: none;
                                                      font-weight: 500;
                                                      text-align: center;
                                                      border-radius: 8px;
                                                      border: 1px solid #000000;
                                                      background-color: #000000 !important;
                                                    "
                                                                            >
                                                                                Verify Email
                                                                            </a>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                
                                                    <!-- Alert Box -->
                                                    <table
                                                            class="force-normal"
                                                            cellspacing="0"
                                                            cellpadding="0"
                                                            border="0"
                                                            width="100%"
                                                            style="
                                          min-width: 100%;
                                          margin: 15px 0 25px 0;
                                          background-color: #ffffff !important;
                                        "
                                                    >
                                                        <tr>
                                                            <td
                                                                    class="gray-box"
                                                                    style="
                                              background-color: #f9f9f9 !important;
                                              border-left: 3px solid #000000 !important;
                                              border-radius: 8px;
                                              padding: 14px;
                                            "
                                                            >
                                                                <p
                                                                        class="normal-text"
                                                                        style="
                                                margin: 0;
                                                font-family: 'Inter', Arial, sans-serif;
                                                font-size: 13px;
                                                color: #333333 !important;
                                                font-weight: 500;
                                                background-color: #f9f9f9 !important;
                                              "
                                                                >
                                                                    This verification link will expire in
                                                                    <strong
                                                                            style="
                                                  font-weight: 600;
                                                  color: #333333 !important;
                                                  background-color: #f9f9f9 !important;
                                                "
                                                                    >30 minutes</strong
                                                                    >
                                                                    for security reasons.
                                                                </p>
                                                            </td>
                                                        </tr>
                                                    </table>
                
                                                    <!-- Disclaimer -->
                                                    <p
                                                            class="normal-text"
                                                            style="
                                          margin: 0 0 30px 0;
                                          font-family: 'Inter', Arial, sans-serif;
                                          font-size: 11px;
                                          color: #555555 !important;
                                          background-color: #ffffff !important;
                                        "
                                                    >
                                                        If you didn't create an account, you can safely ignore
                                                        this email.
                                                    </p>
                
                                                    <!-- Help Section -->
                                                    <table
                                                            class="force-normal"
                                                            cellspacing="0"
                                                            cellpadding="0"
                                                            border="0"
                                                            width="100%"
                                                            style="
                                          min-width: 100%;
                                          border-top: 1px solid #eeeeee;
                                          padding-top: 25px;
                                          background-color: #ffffff !important;
                                        "
                                                    >
                                                        <tr>
                                                            <td
                                                                    align="center"
                                                                    class="force-normal"
                                                                    style="background-color: #ffffff !important"
                                                            >
                                                                <p
                                                                        class="dark-text"
                                                                        style="
                                                margin: 10px;
                                                font-family: 'Inter', Arial, sans-serif;
                                                font-size: 14px;
                                                font-weight: 600;
                                                color: #222222 !important;
                                                background-color: #ffffff !important;
                                              "
                                                                >
                                                                    Need help?
                                                                </p>
                                                                <p
                                                                        class="dark-text"
                                                                        style="
                                                margin: 0;
                                                font-family: 'Inter', Arial, sans-serif;
                                                font-size: 14px;
                                                color: #222222 !important;
                                                background-color: #ffffff !important;
                                              "
                                                                >
                                                                    <a
                                                                            href="mailto:teamoffoura@offoura.com"
                                                                            style="
                                                  color: #000000 !important;
                                                  text-decoration: none;
                                                  font-weight: 600;
                                                  background-color: #ffffff !important;
                                                "
                                                                    >
                                                                        teamoffoura@offoura.com
                                                                    </a>
                                                                </p>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                
                                        <!-- Footer -->
                                        <table
                                                class="force-normal"
                                                cellpadding="0"
                                                cellspacing="0"
                                                border="0"
                                                width="100%"
                                                style="min-width: 100%; background-color: #ffffff !important"
                                        >
                                            <tr>
                                                <td
                                                        align="center"
                                                        class="force-normal"
                                                        style="
                                        padding: 0 40px 30px 40px;
                                        background-color: #ffffff !important;
                                      "
                                                >
                                                    <p
                                                            class="normal-text"
                                                            style="
                                          margin: 0;
                                          font-family: 'Inter', Arial, sans-serif;
                                          font-size: 12px;
                                          color: #666666 !important;
                                          background-color: #ffffff !important;
                                        "
                                                    >
                                                        © 2025 Offoura. All rights reserved.
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                
                <!-- Gmail fix -->
                <div
                        style="
                        white-space: nowrap;
                        font: 15px courier;
                        line-height: 0;
                        opacity: 0.01;
                      "
                >
                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                    &nbsp; &nbsp; &nbsp; &nbsp;
                </div>
                </body>
                </html>""";
    }

    public String getVerificationTemplate(){
        return  this.VerificationTemplate;
    }


    public String getForgotPasswordTemplate(){
        return  this.ForgotPasswordTemplate;
    }



}
